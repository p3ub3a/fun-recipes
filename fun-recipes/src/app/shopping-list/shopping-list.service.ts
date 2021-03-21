import { Subject } from "rxjs";
import { Ingredient } from "../recipes/ingredient.model";

export class ShoppingListService {
    ingredientsChanged = new Subject<Ingredient[]>();
    editingInProgress = new Subject<number>();

    private ingredients: Ingredient[] = [
        new Ingredient("Pasta", 2),
        new Ingredient("Salad", 1)
    ];   
    
    getIngredients(){
        return this.ingredients.slice();
    }

    getIngredient(index: number){
        return this.ingredients[index];
    }

    addIngredient(ingredient: Ingredient){
        this.ingredients.push(ingredient);
        this.ingredientsChanged.next(this.getIngredients());
    }

    updateIngredient(index: number, ingredient: Ingredient){
        this.ingredients[index] = ingredient;
        this.ingredientsChanged.next(this.getIngredients());
    }

    deleteIngredient(index: number){
        this.ingredients.splice(index, 1);
        this.ingredientsChanged.next(this.getIngredients());
    }

    addIngredients(ingredients: Ingredient[]){
        this.ingredients.push(...ingredients);
        this.ingredientsChanged.next(this.ingredients.slice());
    }
}