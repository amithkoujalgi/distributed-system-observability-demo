package io.github.amithkoujalgi.demo;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

@Hidden
@RestController
public class HomeController {
//    @GetMapping(value = "/")
//    RedirectView home() {
//        return new RedirectView("/index.html");
//    }

    @GetMapping("/")
    public String home(ServerHttpResponse response) {
        response.getHeaders().setLocation(URI.create("/index.html"));
        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        return "redirect:/index.html";
    }
}
