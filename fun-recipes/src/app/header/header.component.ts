import { Component } from "@angular/core";
import { RecipeService } from "../recipes/recipes.service";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html'
})
export class HeaderComponent{

    warningMessage: string;
    counter: number;
    refreshCounter: any;

    constructor(private recipeService: RecipeService){
        this.warningMessage = null;
    }

    onSaveRecipes(){
        this.warningMessage = null;
        this.recipeService.storeRecipes().subscribe(response => {
            console.log(response);
        },
        err => {
            this.initCounter(err);
        });
    }

    onGetRecipes(){
        this.warningMessage = null;
        this.recipeService.fetchRecipes().subscribe(response => {
            console.log(response);
        },
        err => {
            this.initCounter(err);
        });
    }

    initCounter(err: any){
        console.log(err);
            clearInterval(this.refreshCounter);
            this.warningMessage = err.error.message;
            this.counter = err.error.timer;
            this.refreshCounter = setInterval(() => {
                if (this.counter <= 0){
                    clearInterval(this.refreshCounter);
                    this.warningMessage = null;
                }
                this.counter--;
            }, 1000);
    }
}