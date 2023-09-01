package com.gaziyev.spring.controller;

import com.gaziyev.spring.dto.PersonDTO;
import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.service.RegistrationService;
import com.gaziyev.spring.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Controller
@RequestMapping("/auth")
public class AuthController {

    PersonValidator personValidator;
    RegistrationService registrationService;
    ModelMapper modelMapper;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/forgotten")
    public String newPasswordPage() { return "auth/forgottenPassword"; }

    @PostMapping("/forgotten")
    public String newPassword(@RequestParam("username") String login,
                              @RequestParam("password") String newPassword) {
        boolean isSuccess = registrationService.updatePassword(login,newPassword);

        if (!isSuccess) {
            return "redirect:/auth/forgotten?error";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") PersonDTO person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid PersonDTO personDTO,
                                                  BindingResult bindingResult) {
        personValidator.validate(personDTO,bindingResult);

        if (bindingResult.hasErrors())
            return "auth/registration";

        registrationService.register(convertToPerson(personDTO));
        return "auth/login";
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO,Person.class);
    }
}

