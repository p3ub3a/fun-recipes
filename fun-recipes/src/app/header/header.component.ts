import { Component } from "@angular/core";
import { RecipeService } from "../recipes/recipes.service";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html'
})
export class HeaderComponent{

    constructor(private recipeService: RecipeService){}

    onSaveRecipes(){
        this.recipeService.storeRecipes();
    }

    onGetRecipes(){
        this.recipeService.fetchRecipes().subscribe();
    }
}