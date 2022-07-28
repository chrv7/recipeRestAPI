package ru.chirva.recipe.app.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chirva.recipe.app.persistence.RecipeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeAll = new ArrayList<>();
        recipeRepository.findAll().forEach(recipeAll::add);
        return recipeAll;
    }

    public Optional<Recipe> findById(Long id) {
        if (recipeRepository.findById(id).isPresent()) {
            return Optional.of(recipeRepository.findById(id).get());
        } else {
            return Optional.empty();
        }
    }

    public Long saveRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        Recipe savedRecipe = recipeRepository.save(recipe);
        return savedRecipe.getId();
    }

    public void updateRecipe(Long id, Recipe recipe) {
        Recipe recipeInDB = recipeRepository.findById(id).get();
        recipeInDB.setName(recipe.getName());
        recipeInDB.setCategory(recipe.getCategory());
        recipeInDB.setDescription(recipe.getDescription());
        recipeInDB.setIngredients(recipe.getIngredients());
        recipeInDB.setDirections(recipe.getDirections());
        recipeInDB.setDate(LocalDateTime.now());
        recipeRepository.save(recipeInDB);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
    }
}
