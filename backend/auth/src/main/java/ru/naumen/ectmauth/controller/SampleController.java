package ru.naumen.ectmauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.naumen.ectmauth.config.SocialNetworkConf;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SampleController {

    private final SocialNetworkConf conf;

    @GetMapping("/sample")
    String sample() {
        return conf.getVkSecretKey();
    }
}
