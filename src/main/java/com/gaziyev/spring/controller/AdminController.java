package com.gaziyev.spring.controller;

import com.gaziyev.spring.security.PasswordComparison;
import com.gaziyev.spring.security.PersonDetails;
import com.gaziyev.spring.service.AdminService;
import com.gaziyev.spring.util.PasswordValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Controller
@RequestMapping("/admin")
public class AdminController {
    PasswordValidator passwordValidator;
    PasswordComparison passwordComparison;
    AdminService adminService;

    @GetMapping("/changeRole")
    public String changeUserRole(@ModelAttribute("comparison") PasswordComparison comparison) {
        return "admin/changeRole";
    }

    @GetMapping("/check")
    public String checkingPassword(@ModelAttribute("comparison") PasswordComparison actualPassword,
                                   BindingResult bindingResult, Model model) {
        passwordComparison.setActualPassword(actualPassword.getActualPassword());
        passwordValidator.validate(passwordComparison, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("validPassword", false);
            return "admin/changeRole";
        }

        model.addAttribute("validPassword", true);
        return "admin/changeRole";
    }

    @PatchMapping("/changeRole")
    public String updateRole(@RequestParam String newRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        adminService.changeRole(personDetails.person(),newRole);

        return "redirect:/auth/login";
    }

    @GetMapping("/categories")
    public String showCategories() {
        return "admin/categories";
    }

    @GetMapping("/people")
    public String redirectToPerson() {
        return "redirect:/people";
    }

    @GetMapping("/books")
    public String redirectToBook() {
        return "redirect:/book";
    }

    @GetMapping("/search")
    public String redirectToSearch() {
        return "redirect:/book/search";
    }
}
