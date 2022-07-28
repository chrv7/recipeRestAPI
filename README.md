# RecipeRestAPI
The program is a multi-user web service based on Spring Boot that allows storing, retrieving, updating, and deleting recipes. The service uses H2 database.
The service has 2 main entity: User and Recipe. The service supports the registration process. User can update or delete only their recipes, but can view recipes added by others users.  
### Endpoints
* [localhost:8080/api/recipe/]() - RecipeService
* [localhost:8080/api/register]() - UserService

### UserService

Method: `POST`  
Path: `api/register`  
**Example:** request without authentication

    {
        "email": "test@some.com",
        "password": "testpass1"
    }

Status code: `200 (Ok)`

### RecipeService

Method: `POST`  
Path: `api/recipe/new`  
**Example:** request without authentication

    {
        "name": "Fresh Mint Tea",
        "category": "beverage",
        "description": "Light, aromatic and refreshing beverage, ...",
        "ingredients": ["boiled water", "honey", "fresh mint leaves"],
        "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
    }
Status code: `401 (Unauthorized)`

**Example:** request with basic authentication; email (login): test1@some.com and password testpass1

    {
        "name": "Mint Tea",
        "category": "beverage",
        "description": "Light, aromatic and refreshing beverage, ...",
        "ingredients": ["boiled water", "honey", "fresh mint leaves"],
        "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
    }
Response:
    
    {
        "id": 1
    }
***
Method: `PUT`  
Path: `api/recipe/1`  
**Example:** change name recipe. Request with basic authentication; email (login): test1@some.com and password testpass1

    {
        "name": "Fresh Mint Tea",
        "category": "beverage",
        "description": "Light, aromatic and refreshing beverage, ...",
        "ingredients": ["boiled water", "honey", "fresh mint leaves"],
        "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
    }
Status code: `204 (No Content)`

**Example:** change recipe by another user. Request with basic authentication; email (login): test2@some.com and password testpass2

    {
        "name": "Warming Ginger Tea",
        "category": "beverage",
        "description": "Ginger tea is a warming drink for cool weather, ...",
        "ingredients": ["1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"],
        "directions": ["Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)", "Steep for 5-10 minutes", "Drink and enjoy"]
    }
Status code: `403 (Forbidden)`
***
Method: `GET`  
Path: `api/recipe/{id}`
**Example:** get recipe by id = 1. Request with basic authentication; email (login): test2@some.com and password testpass2  
Response:

    {
        "name": "Fresh Mint Tea",
        "category": "beverage",
        "description": "Light, aromatic and refreshing beverage, ...",
        "ingredients": ["boiled water", "honey", "fresh mint leaves"],
        "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
    }
***
Method: `DELETE`  
Path: `api/recipe/{id}`
**Example:** delete recipe by id = 1. Request with basic authentication; email (login): test2@some.com and password testpass2.  
Status code: `403 (Forbidden)`

**Example:** delete recipe by id = 1. Request with basic authentication; email (login): test1@some.com and password testpass1.  
Status code: `204 (No Content)`