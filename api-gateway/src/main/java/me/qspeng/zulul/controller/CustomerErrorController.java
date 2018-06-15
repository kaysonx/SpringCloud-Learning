package me.qspeng.zulul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/customerError")
public class CustomerErrorController {

    @RequestMapping("/error")
    public String errorHandler() {
        return "Some error occurred...";
    }
}

