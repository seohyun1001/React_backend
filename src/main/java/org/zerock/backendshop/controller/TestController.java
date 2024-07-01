package org.zerock.backendshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
  @GetMapping("/authentication/home")
  public String home(){
    return "Hello World";
  }


  @GetMapping("/test")
  public String test(){
    return "text";
  }
}
