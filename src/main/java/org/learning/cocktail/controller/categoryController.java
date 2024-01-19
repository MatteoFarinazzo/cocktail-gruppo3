package org.learning.cocktail.controller;

import jakarta.validation.Valid;
import org.learning.cocktail.model.Category;
import org.learning.cocktail.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class categoryController {

     @Autowired
    private CategoryRepository categoryRepository;

     // METODO CHE MOSTRA LE CATEGORIE
     @GetMapping
    public String index(Model model) {
         model.addAttribute("categoryList", categoryRepository.findAll());
         return "categories/list";
     }

     // METODO CHE MOSTRA LA PAGINA FORM DELLE CATEGORIE
     @GetMapping("/create")
    public String create(Model model) {
         model.addAttribute("formCategory", new Category());
         return "categories/create";
     }

     @PostMapping("/create")
    public String store(@Valid @ModelAttribute("formCategory") Category formCategory, BindingResult bindingResult) {
         if (bindingResult.hasErrors()) {
             return "categories/create";
         }
         categoryRepository.save(formCategory);
         return "redirect:/categories";
     }

     @GetMapping("edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
         Optional<Category> result = categoryRepository.findById(id);
         if(result.isPresent()) {
             model.addAttribute("categoria", result.get());
             return "categories/create";
         } else {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le categorie con id " + id + " non sono state trovate");
         }
     }

     @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult) {
         Optional<Category> result = categoryRepository.findById(id);
         if(result.isPresent()) {
             Category categoryToEdit = result.get();
             if(bindingResult.hasErrors()) {
                 return "categories/create";
             }
             Category savedCategory = categoryRepository.save(formCategory);
             return "redirect:/categories";
         } else {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le categorie con id " + id + " non sono state trovate");
         }
     }

     // METODO PER CANCELLARE LE CATEGORIE PER ID
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
         Optional<Category> result = categoryRepository.findById(id);
         if(result.isPresent()) {
             categoryRepository.deleteById(id);
             return "redirect:/categories";
         } else {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le categorie con id " + id + " non sono state trovate");
         }
    }






}
