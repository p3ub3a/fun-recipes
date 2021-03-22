import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from "rxjs";
import { map,tap } from "rxjs/operators";
import { ShoppingListService } from "../shopping-list/shopping-list.service";
import { Ingredient } from "./ingredient.model";
import { Recipe } from "./recipe.model";

@Injectable()
export class RecipeService {
    recipesChanged = new Subject<Recipe[]>();
    private recipes: Recipe[] = [];
    //     new Recipe("Chicken pasta bake", 
    //     "Enjoy this gooey cheese and chicken pasta bake for the ultimate weekday family dinner. Serve straight from the dish with a dressed green salad.", 
    //     "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chicken_pasta_bake-06fe2d6.jpg?quality=90&webp=true&resize=300,272",
    //     [
    //         new Ingredient("pasta", 1),
    //         new Ingredient("chicken breast", 1),
    //         new Ingredient("cheese", 1),
    //         new Ingredient("salad", 1)
    //     ]),
    //     new Recipe("Classic lasagne", 
    //     "Prepare this easy lasagne ahead of time and save in the freezer, uncooked, for when you need it during a busy week. Then just bake for an extra 45 mins.", 
    //     "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/recipe-image-legacy-id-1273579_7-9c7cfc0.jpg?quality=90&webp=true&resize=300,272",
    //     [
    //         new Ingredient("lean beef mince", 750),
    //         new Ingredient("pack prosciutto", 90),
    //         new Ingredient("olive oil", 1),
    //         new Ingredient("tomato sauce", 100),
    //         new Ingredient("hot beef stock", 200),
    //         new Ingredient("lasagna sheets", 300)
    //     ])
    // ];

    constructor(private shoppingListService: ShoppingListService, private http: HttpClient){

    }

    storeRecipes(){
        const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
        return this.http.post("http://localhost:8080/recipes", this.getRecipes(), {headers: headers});
    }

    fetchRecipes(){
        const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
        return this.http.get<Recipe[]>("http://localhost:8080/recipes", {headers: headers})
            .pipe(
                map(recipes => {
                    console.log(recipes);
                    return recipes.map(recipe => {
                        return {...recipe, ingredients: recipe.ingredients ? recipe.ingredients : []};
                    });
                }),
                tap(recipes => {
                    this.setRecipes(recipes);
                })
            );
    }

    setRecipes(recipes: Recipe[]){
        this.recipes = recipes;
        this.recipesChanged.next(this.getRecipes());
    }

    getRecipes(){
        return this.recipes.slice();
    }

    getRecipe(id: number){
        return this.recipes.slice()[id];
    }

    transferIngredients(ingredients: Ingredient[]){
        this.shoppingListService.addIngredients(ingredients);
    }

    addRecipe(recipe: Recipe){
        this.recipes.push(recipe);
        this.recipesChanged.next(this.getRecipes());
    }

    updateRecipe(index: number, newRecipe: Recipe){
        this.recipes[index] = newRecipe;
        this.recipesChanged.next(this.getRecipes());
    }

    deleteRecipe(index: number){
        this.recipes.splice(index,1);
        this.recipesChanged.next(this.getRecipes());
    }
}