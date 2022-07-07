package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.businesslayer.*;
import recipes.persistence.UserRepository;

import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/recipe/{id}")
    public Map<String, Object> getRecipe(@PathVariable long id) {
        try {
            if (recipeService.findById(id).isPresent()) {
                Recipe recipeFound = recipeService.findById(id).get();
                Map<String, Object> recipeToReturn = new LinkedHashMap<>();
                recipeToReturn.put("name", recipeFound.getName());
                recipeToReturn.put("category", recipeFound.getCategory());
                recipeToReturn.put("date", recipeFound.getDate());
                recipeToReturn.put("description", recipeFound.getDescription());
                recipeToReturn.put("ingredients", recipeFound.getIngredients());
                recipeToReturn.put("directions", recipeFound.getDirections());
                return recipeToReturn;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> saveRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findUserByEmail(userDetails.getUsername()).get();
        recipe.setUser(user);
        long newId = recipeService.saveRecipe(recipe);
        Map<String, Long> returnId = new HashMap<>(1){
            {
                put("id", newId);
            }
        };

        return returnId;
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@Valid @PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (recipeService.findById(id).isPresent()) {
            if (userDetails.getUsername().equals(recipeService.findById(id).get().getUser().getEmail())) {
                recipeService.deleteRecipe(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails userDetails) {
        if (recipeService.findById(id).isPresent()) {
            if (userDetails.getUsername().equals(recipeService.findById(id).get().getUser().getEmail())) {
                recipeService.updateRecipe(id, recipe);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/recipe/search/")
    @ResponseBody
    public ResponseEntity<?> searchRecipe(@RequestParam(required = false, name = "category") String category,
                                     @RequestParam(required = false, name = "name") String name) {
        if ((category == null || category.isEmpty()) && (name == null || name.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (category != null && !category.isEmpty()) {
            return new ResponseEntity<Object>(recipeService.findByCategory(category), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(recipeService.findByName(name), HttpStatus.OK);
        }
    }
}
