package org.learning.cocktail.repository;


import org.learning.cocktail.model.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepository extends JpaRepository<Cocktail, Integer> {
}
