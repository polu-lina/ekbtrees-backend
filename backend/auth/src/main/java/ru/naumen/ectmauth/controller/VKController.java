package ru.naumen.ectmauth.controller;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmauth.jwtGenerator.JWTService;
import ru.naumen.ectmauth.user.Provider;
import ru.naumen.ectmauth.token.Token;
import ru.naumen.ectmauth.user.User;
import ru.naumen.ectmauth.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/auth/vk")
public class VKController {

    private final String clientSecret = "N8BemovEaCQgFBMvQRRq";
    private final int clientId = 7835777;
    private final String host = "localhost";
    private final Integer port = 8080;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JWTService jwtService;

    TransportClient transportClient = HttpTransportClient.getInstance();
    VkApiClient vk = new VkApiClient(transportClient);


    public VKController() throws ClientException, ApiException {

    }

    @Operation(summary = "Авторизоваться через Вконтакте")
    @PostMapping("/authorize")
    @ResponseBody
    public void authorize(@RequestBody(required = false) Map<String, String> json, HttpServletResponse response) throws IOException {
        System.out.println("мы в authorize");
        String redirAuth = getOAuthUrl();
        System.out.println(redirAuth);
        response.sendRedirect(redirAuth);

    }

    @GetMapping("/callback")
    public void callback(@RequestParam("code") String code, HttpServletResponse response) throws ServletException, ClientException, ApiException, IOException {

        try {

            UserAuthResponse authResponse = vk.oAuth().userAuthorizationCodeFlow(clientId, clientSecret, getRedirectUri(), code).execute();
            User user = userService.findByVk_id(authResponse.getUserId());
            if (user == null) {
                user = createNewUser(authResponse.getUserId(), authResponse.getAccessToken());
            }
            System.out.println(1);
            Map<String, String> tokens = jwtService.createNewTokensWithVK(user.getUser_id(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getProvider(), authResponse.getAccessToken());
            Cookie cookie_access_token = new Cookie("access_token", tokens.get("access_token"));
            cookie_access_token.setHttpOnly(true);
            response.addCookie(cookie_access_token);
            Cookie cookie_refresh_token = new Cookie("refresh_token", tokens.get("refresh_token"));
            cookie_refresh_token.setHttpOnly(true);
            response.addCookie(cookie_refresh_token);
            Cookie cookie_access_token_VK = new Cookie("access_token_VK", authResponse.getAccessToken());
            cookie_access_token_VK.setHttpOnly(true);
            response.addCookie(cookie_access_token_VK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            response.getWriter().println("error");
            response.setContentType("text/html;charset=utf-8");
            e.printStackTrace();
        }

    }

    private String getOAuthUrl() {

        return "https://oauth.vk.com/authorize?client_id=" + clientId + "&display=page&redirect_uri=" + getRedirectUri() + "&scope=groups&response_type=code";
    }

    private String getRedirectUri() {
        return String.format("http://%s:%d", host, port) + "/callback";
    }

    private String getInfoPage(GetResponse user) {
        return "Hello <a href='https://vk.com/id" + user.getId() + "'>" + user.getFirstName() + "</a>";
    }

    private User createNewUser(Integer user_vk_id, String token) {

        User user = new User();
        try {
            UserActor actor = new UserActor(user_vk_id, token);
            List<GetResponse> getUsersResponse = null;

            getUsersResponse = vk.users().get(actor).userIds(String.valueOf(user_vk_id)).execute();

            GetResponse userR = getUsersResponse.get(0);


            user.setFirstName(userR.getFirstName());
            user.setLastName(userR.getLastName());
            user.setVk_id(userR.getId());
            user.setProvider(Provider.VK);

            Token t = new Token();
            t.setAccess_token_VK(token);
            Set<Token> tokenSet = new HashSet<>();
            tokenSet.add(t);
            user.setTokens(tokenSet);
            userService.save(user);

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return user;
    }
}
