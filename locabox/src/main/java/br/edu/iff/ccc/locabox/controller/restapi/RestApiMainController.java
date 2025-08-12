package br.edu.iff.ccc.locabox.controller.restapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/api/v1")
public class RestApiMainController {
    
    @GetMapping("")
    public ResponseEntity<String> getApiHome() {
        return ResponseEntity.ok().header("content-type", "application/json").body("{\"message\": \"Bem-vindo à API LocaBox!\"}");
    }
    
}
