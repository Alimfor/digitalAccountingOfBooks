package com.gaziyev.spring.controller;

import com.gaziyev.spring.dto.BookDTO;
import com.gaziyev.spring.dto.PersonDTO;
import com.gaziyev.spring.model.Book;
import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.service.BooksService;
import com.gaziyev.spring.service.PeopleService;
import com.gaziyev.spring.util.BookValidator;
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

import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Controller
@RequestMapping("/book")
public class BookController {
    BooksService booksService;
    PeopleService peopleService;
    BookValidator bookValidator;
    ModelMapper modelMapper;

    @GetMapping()
    public String index(Model model,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "10") int size,
                        @RequestParam(required = false, defaultValue = "false") boolean sort_by_year
    ) {
        model.addAttribute(
                "books",
                booksService.findAll(page, size, sort_by_year)
                        .stream().map(this::convertBookToBookDTO)
                        .toList()
        );
        return "admin/books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") PersonDTO personDTO) {
        BookDTO bookDTO = convertBookToBookDTO(booksService.findOne(id));

        model.addAttribute("book", bookDTO);
        model.addAttribute("personWithBook", bookDTO.getPerson());
        model.addAttribute(
                "people",
                peopleService.findAll()
                        .stream().map(this::convertPersonToPersonDTO)
                        .toList()
        );
        return "admin/books/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String book_name,
                         @RequestParam(required = false, defaultValue = "0") int page,
                         @RequestParam(required = false, defaultValue = "10") int size,
                         Model model
    ) {
        boolean isBookNameNotNull = book_name != null;
        if (isBookNameNotNull) {
            List<BookDTO> books = booksService.findByNameStartingWith(book_name, page, size)
                    .stream().map(this::convertBookToBookDTO)
                    .toList();

            model.addAttribute("books", books);
        }
        model.addAttribute("isBookNameNotNull", isBookNameNotNull);

        return "admin/books/search";
    }

    @PatchMapping("/link")
    public String linkBookToPerson(@ModelAttribute("person") PersonDTO personDTO,
                                   @ModelAttribute("bookId") int bookId) {
        booksService.updateBookPersonRelationship(bookId, personDTO.getId());
        return "redirect:/book/" + bookId;
    }

    @PatchMapping("/release")
    public String releaseTheBook(@ModelAttribute("bookId") int bookId) {
        booksService.removePersonFromBook(bookId);
        return "redirect:/book/" + bookId;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") BookDTO bookDTO) {
        return "admin/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid BookDTO bookDTO,
                         BindingResult bindingResult) {
        bookValidator.validate(convertBookDTOToBook(bookDTO),bindingResult);
        if (bindingResult.hasErrors()) return "admin/books/new";

        booksService.save(convertBookDTOToBook(bookDTO));
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute(
                "book",
                convertBookToBookDTO(
                        booksService.findOne(id)
                )
        );
        return "admin/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid BookDTO bookDTO,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookValidator.validate(convertBookDTOToBook(bookDTO),bindingResult);
        if (bindingResult.hasErrors()) return "admin/books/edit";

        booksService.update(convertBookDTOToBook(bookDTO));
        return "redirect:/book/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/book";
    }

    private BookDTO convertBookToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    private Book convertBookDTOToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    private PersonDTO convertPersonToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
