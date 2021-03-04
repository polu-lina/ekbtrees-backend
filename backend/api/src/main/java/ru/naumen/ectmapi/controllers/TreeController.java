package ru.naumen.ectmapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tree")
public class TreeController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world!";
    }

}
