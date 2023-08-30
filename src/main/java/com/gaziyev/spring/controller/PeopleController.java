package com.gaziyev.spring.controller;

import com.gaziyev.spring.dto.PersonDTO;
import com.gaziyev.spring.security.PersonDetails;
import com.gaziyev.spring.service.AdminService;
import com.gaziyev.spring.service.BooksService;
import com.gaziyev.spring.util.PersonValidator;
import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.service.PeopleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final BooksService booksService;
    private final PersonValidator personValidator;
    private final AdminService adminService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService, PersonValidator personValidator, AdminService adminService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.personValidator = personValidator;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(
                "people",
                peopleService.findAll()
                        .stream().map(this::convertToPersonDTO)
                        .toList()
        );
        return "admin/people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        PersonDTO personDTO = convertToPersonDTO(peopleService.findOne(id));

        model.addAttribute("person", personDTO);
        model.addAttribute("currentBooks", personDTO.getBooks());

        return "admin/people/show";
    }

    @PatchMapping("/release")
    public String releaseTheBook(@ModelAttribute("bookId") int bookId,
                                 @ModelAttribute("personId") int personId) {
        booksService.removePersonFromBook(bookId);
        return "redirect:/people/" + personId;
    }


    @PatchMapping("/{id}/lock")
    public String lockUser(@PathVariable("id") int id) {
        adminService.lockUser(id);
        return "redirect:/people/{id}";
    }
    @PatchMapping("/{id}/activate")
    public String activateUser(@PathVariable("id") int id) {
        adminService.activateUser(id);
        return "redirect:/people/{id}";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") PersonDTO personDTO) {
        return "admin/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid PersonDTO personDTO,
                         BindingResult bindingResult) {
        personValidator.validate(personDTO, bindingResult);
        if (bindingResult.hasErrors()) {
             return "admin/people/new";
        }


        peopleService.save(
                convertToPerson(personDTO)
        );
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute(
                "person",
                convertToPersonDTO(
                        peopleService.findOne(id)
                )
        );
        return "admin/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid PersonDTO personDTO,
                         BindingResult bindingResult) {
        personValidator.validate(personDTO, bindingResult);
        if (bindingResult.hasErrors()) return "admin/people/edit";

        peopleService.update(convertToPerson(personDTO));
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        String whatIsTheRole = peopleService.findOne(id).getRole();
        peopleService.delete(id);

        if (whatIsTheRole.equals("ROLE_ADMIN")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

            int authenticatedId = personDetails.person().getId();
            if (authenticatedId == id)
                return "redirect:/auth/login";
        }
        return "redirect:/people";
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
