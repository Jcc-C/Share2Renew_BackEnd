package com.share2renew.controller;

/**
 * @program: Share2Renew_BackEnd
 * @description:
 * @author: Junxian Cai
 **/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Use for testing
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String Hello() {
        return "Hello Spring Boot";
    }
}
