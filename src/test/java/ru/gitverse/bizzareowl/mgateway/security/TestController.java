package ru.gitverse.bizzareowl.mgateway.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/test")
    public void testPostRequest() {

    }

}
