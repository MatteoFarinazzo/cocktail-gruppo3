package org.learning.cocktail.controller;

import jakarta.validation.Valid;
import org.learning.cocktail.model.Cocktail;
import org.learning.cocktail.repository.CategoryRepository;
import org.learning.cocktail.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cocktails")
public class cocktailController {
    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public String index(Model model) {
        List<Cocktail> cocktailList;
        cocktailList = cocktailRepository.findAll();
        model.addAttribute("cocktailList", cocktailList);
        return "cocktails/list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Cocktail> result = cocktailRepository.findById(id);
        if (result.isPresent()) {
            Cocktail cocktail = result.get();
            model.addAttribute("cocktail", cocktail);
            return "cocktails/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        Cocktail cocktail = new Cocktail();
        model.addAttribute("cocktail", cocktail);
        return "cocktails/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("cocktail") Cocktail formCocktail, Model model) {
        Cocktail savedCocktail = cocktailRepository.save(formCocktail);
        return "redirect:/show/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Cocktail> result = cocktailRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("cocktail", result.get());
            return "cocktails/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cocktail with id" + id + "not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("cocktail") Cocktail formCocktail, BindingResult bindingResult) {
        Optional<Cocktail> result = cocktailRepository.findById(id);
        if (result.isPresent()) {
            Cocktail cocktailToEdit = result.get();
            if (bindingResult.hasErrors()) {
                return "cocktails/edit";
            }
            formCocktail.setImage(cocktailToEdit.getImage());
            Cocktail savedPizza = cocktailRepository.save(formCocktail);
            return "redirect:/cocktails/show/";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cocktail with id" + id + "not found");
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Cocktail> result = cocktailRepository.findById(id);
        if (result.isPresent()) {
            cocktailRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("redirectMessage", result.get().getName() + " è stato cancellato!");
            return "redirect:/cocktails";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cocktail with id" + id + "not found");
        }
    }

}
