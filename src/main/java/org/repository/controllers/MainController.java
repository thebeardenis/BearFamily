package org.repository.controllers;

import jakarta.validation.Valid;
import org.repository.DAO.UserDAO;
import org.repository.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private UserDAO ud;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String goToHome(Model model) {
        model.addAttribute("userPage", null);
        return "homePage.html";
    }

    @GetMapping("/home/{id}")
    public String goToUserHome(@PathVariable("id") String id, Model model) {
        model.addAttribute("userPage", ud.showUser(id));
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

    @GetMapping("/login")
    public String goToLogin(Model model) {
        model.addAttribute("user", new User());
        return "loginPage.html";
    }

    @PostMapping("/login")
    public String PostToLogin(@ModelAttribute("user") @Valid User user) {
        ud.save(user);
        return "redirect:/home/" + user.getId();
    }

    @GetMapping("/profile/{id}")
    public String goToProfile(@PathVariable("id") String id,Model model) {
        model.addAttribute("user", ud.showUser(id));
        return "profilePage.html";
    }

}
