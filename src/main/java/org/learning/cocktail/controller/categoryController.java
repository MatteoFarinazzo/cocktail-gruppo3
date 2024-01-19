package org.learning.cocktail.controller;

import org.learning.cocktail.Model.Category;
import org.learning.cocktail.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class categoryController {

     @Autowired
    private CategoryRepository categoryRepository;

     @GetMapping
    public String index(Model model) {
         model.addAttribute("categoryList", categoryRepository.findAll());
         return "categories/";
     }

     @GetMapping("/create")
    public String create(Model model) {
         model.addAttribute("formCategory", new Category());
         return "categories/";
     }





}
