package com.devopsbuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by larris on 27/08/16.
 */

@Controller
public class HelloworldController {
    @RequestMapping("/")
    public String sayHello(){
        return "index";
    }
}
