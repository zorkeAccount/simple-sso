package com.sheefee.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zorke on 2017/11/5.
 */
@RestController
public class TestApiController extends JsonController {

    @RequestMapping("/pub/api")
    public String pubApi() {
        return "public api";
    }

    @RequestMapping("/auth/api")
    public String authApi() {
        return "auth api";
    }

}
