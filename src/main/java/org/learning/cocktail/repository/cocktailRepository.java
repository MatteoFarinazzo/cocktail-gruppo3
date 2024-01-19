package org.learning.cocktail.repository;

import org.learning.cocktail.Model.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cocktailRepository extends JpaRepository<Cocktail, Integer> {
}
