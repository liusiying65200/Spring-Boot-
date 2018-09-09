package net.hualisheng.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @RequestMapping(value = "/user/home")
    @ResponseBody
    public String home(){
        return "user home";
    }
}
