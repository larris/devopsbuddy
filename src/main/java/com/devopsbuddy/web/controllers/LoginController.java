package com.devopsbuddy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by larris on 01/09/16.
 */

@Controller
public class LoginController {

    public  static  final  String LOGIN_VIEW_NAME = "user/login";

    @RequestMapping("/login")
    public  String login (){
        return  LOGIN_VIEW_NAME;
    }

}
