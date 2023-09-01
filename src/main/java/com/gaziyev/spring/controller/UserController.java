package com.gaziyev.spring.controller;

import com.gaziyev.spring.dto.PersonDTO;
import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.security.PersonDetails;
import com.gaziyev.spring.service.UserService;
import com.gaziyev.spring.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Controller
@RequestMapping("/user")
public class UserController {
    UserService userService;
    ModelMapper modelMapper;
    PersonValidator personValidator;

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "error/accessDenied";
    }

    @GetMapping("/infoAbout")
    public String getCategories(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        PersonDTO personDTO = convertToPersonDTO(userService.find(personDetails.person().getId()));

        model.addAttribute("person",personDTO);
        return "user/infoAbout";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        PersonDTO personDTO = convertToPersonDTO(userService.find(id));

        model.addAttribute("person", personDTO);
        model.addAttribute("currentBooks", personDTO.getBooks());

        return "user/show";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute(
                "person",
                convertToPersonDTO(
                        userService.find(id)
                )
        );
        return "user/edit";
    }

    @GetMapping("/{id}/changePassword")
    public String updatePasswordPage(Model model, @PathVariable("id") int id) {
        model.addAttribute(
                "person",
                convertToPersonDTO(
                        userService.find(id)
                )
        );
        return "user/changePassword";
    }

    @PatchMapping("/{id}/change")
    public String updatePassword(@RequestParam("password") String newPassword,
                                 @PathVariable("id") int id) {
        userService.updatePassword(id,newPassword);

        return "redirect:/auth/login";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid PersonDTO personDTO,
                         BindingResult bindingResult) {
        personValidator.validate(personDTO, bindingResult);
        if (bindingResult.hasErrors()) return "user/edit";

        userService.update(convertToPerson(personDTO));
        return "redirect:/user/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/auth/login";
    }

    @GetMapping("/{id}/book")
    public String showBooks(@PathVariable("id") int id,
                            Model model) {
        model.addAttribute("books",convertToPersonDTO(userService.find(id)).getBooks());
        return "user/books";
    }

    @PatchMapping("/release")
    public String releaseTheBook(@ModelAttribute("bookId") int bookId,
                                 @ModelAttribute("personId") int personId) {
        userService.removeUserFromBook(bookId);
        return "redirect:/user/" + personId + "/book";
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person,PersonDTO.class);
    }
    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO,Person.class);
    }
}
