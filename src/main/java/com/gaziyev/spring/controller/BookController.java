package com.gaziyev.spring.controller;

import com.gaziyev.spring.models.Book;
import com.gaziyev.spring.models.Person;
import com.gaziyev.spring.services.BooksService;
import com.gaziyev.spring.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/book")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",booksService.findAll());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person")Person person) {
        Book book = booksService.findOne(id);

        model.addAttribute("book",book);
        model.addAttribute("people",peopleService.findAll());
        model.addAttribute("personWithBook",peopleService.findOne(book.getPerson() == null ? 0 : book.getPerson().getId()));
        return "book/show";
    }

    @PatchMapping("/link")
    public String linkBookToPerson(@ModelAttribute("person") Person person,
                                   @ModelAttribute("bookId") int bookId) {
        booksService.updateBookPersonRelationship(bookId,person.getId());
        return "redirect:/book/" + bookId;
    }

    @PatchMapping("/release")
    public String releaseTheBook(@ModelAttribute("bookId") int bookId) {
        booksService.removePersonFromBook(bookId);
        return "redirect:/book/" + bookId;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()) return "book/new";

        booksService.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book",booksService.findOne(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id)
    {
        if (bindingResult.hasErrors()) return "book/edit";

        booksService.update(id,book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/book";
    }
}
