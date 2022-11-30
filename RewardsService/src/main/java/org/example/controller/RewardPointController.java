package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardPointController {

    @GetMapping
    public String test(){
        return "testSuccessful";
    }

}
