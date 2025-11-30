package com.example.chatapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hello", description = "健康檢查／測試用 API")
@RestController
public class HelloController {

    @Operation(
            summary = "測試用 Hello API",
            description = "簡單回傳字串，用來確認後端服務是否正常運作"
    )
    @GetMapping("/api/hello")
    public String sayHello() {
        return "Hello from Spring boot";
    }

}
