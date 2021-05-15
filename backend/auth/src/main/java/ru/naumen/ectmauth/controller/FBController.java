package ru.naumen.ectmauth.controller;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmauth.config.SocialNetworkConf;
import ru.naumen.ectmauth.entity.Provider;
import ru.naumen.ectmauth.entity.Token;
import ru.naumen.ectmauth.service.JWTService;
import ru.naumen.ectmauth.service.UserService;
import ru.naumen.ectmauth.service.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/auth/fb")
public class FBController {

    private final UserService userService;
    private final JWTService jwtService;
    private final SocialNetworkConf conf;

    private final String appSecret = System.getenv("FB_APP_SECRET_KEY");
    private final String appId = System.getenv("FB_APP_ID");
    private final String host = System.getenv("HOST");
    private final Integer port = Integer.valueOf(System.getenv("PORT"));

    private FacebookConnectionFactory factory = new FacebookConnectionFactory(appId,
            appSecret);


    @Operation(summary = "Авторизоваться через Facebook")
    @GetMapping("/authorize")
    public String producer() {

        OAuth2Operations operations = factory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();

        params.setRedirectUri(String.format("http://%s:%d", host, port)+"/auth/fb/callback");
        params.setScope("email,public_profile");

        String url = operations.buildAuthenticateUrl(params);
        System.out.println("The redirect URL is " + url);
        return "redirect:" + url;

    }

    @Hidden
    @GetMapping(value = "/callback")
    public void prodducer(@RequestParam("code") String authorizationCode, HttpServletResponse response) {
        OAuth2Operations operations = factory.getOAuthOperations();
        AccessGrant accessToken = operations.exchangeForAccess(authorizationCode, String.format("http://%s:%d", host, port)+"/auth/fb/callback",
                null);

        Connection<Facebook> connection = factory.createConnection(accessToken);
        Facebook facebook = connection.getApi();
        String[] fields = {"id", "email", "first_name", "last_name"};
        User userProfile = facebook.fetchObject("me", User.class, fields);


        ru.naumen.ectmauth.entity.User user = userService.findByFb_id(userProfile.getId());
        if (user == null) {
            user = createNewUser( accessToken);
        }
        System.out.println(1+" callback_fb "+user.getFirstName()+" "+user.getLastName()+" "+user.getFb_id());

        Map<String, String> tokens = jwtService.createNewTokensWithSocialNetwork(user.getUser_id(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getProvider(), accessToken.getAccessToken());
        Cookie cookie_access_token = new Cookie("access_token", tokens.get("access_token"));
        cookie_access_token.setHttpOnly(true);
        response.addCookie(cookie_access_token);
        Cookie cookie_refresh_token = new Cookie("refresh_token", tokens.get("refresh_token"));
        cookie_refresh_token.setHttpOnly(true);
        response.addCookie(cookie_refresh_token);
        Cookie cookie_access_token_FB = new Cookie("access_token_FB", accessToken.getAccessToken());
        cookie_access_token_FB.setHttpOnly(true);
        response.addCookie(cookie_access_token_FB);

    }

    private ru.naumen.ectmauth.entity.User createNewUser( AccessGrant accessToken) {

        ru.naumen.ectmauth.entity.User user = new ru.naumen.ectmauth.entity.User();


            Connection<Facebook> connection = factory.createConnection(accessToken);
            Facebook facebook = connection.getApi();
            String[] fields = {"id", "email", "first_name", "last_name"};
            User userProfile = facebook.fetchObject("me", User.class, fields);

            user.setFirstName(userProfile.getFirstName());
            user.setLastName(userProfile.getLastName());
            user.setFb_id(userProfile.getId());
            user.setProvider(Provider.FACEBOOK);

            Token t = new Token();
            t.setAccess_token_FB(accessToken.getAccessToken());
            Set<Token> tokenSet = new HashSet<>();
            tokenSet.add(t);
            user.setTokens(tokenSet);
            userService.save(user);

        return user;
    }
}


