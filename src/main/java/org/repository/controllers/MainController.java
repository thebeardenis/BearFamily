package org.repository.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String goToHome() {
        return "homePage.html";
    }

    @GetMapping("/pricing")
    public String goToPricing() {
        return "pricingPage.html";
    }

    @GetMapping("/contacts")
    public String goToContacts() {
        return "contactsPage.html";
    }
}
