import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { Recipe } from "./recipe.model";
import { RecipeService } from "./recipes.service";

@Injectable({
    providedIn: 'root'
})
export class RecipesResolverService implements Resolve<Recipe[]>{
    
    constructor(private recipeService: RecipeService){

    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Recipe[] | Observable<Recipe[]> | Promise<Recipe[]> {
        const recipes = this.recipeService.getRecipes();
        if(recipes.length === 0){
            return this.recipeService.fetchRecipes();
        }else{
            return recipes;
        }
    }

}