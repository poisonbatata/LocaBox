package br.edu.iff.ccc.webdevg2.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(path = "/principal")
public class MainViewController {

    @GetMapping()
    public String getHomePage() {
        return "home.html";
    }

}
