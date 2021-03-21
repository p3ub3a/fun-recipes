package com.funrecipes.dto;

public class IngredientDTO {
	private String name;
    private int amount;
    
    public IngredientDTO() {}
    
	public IngredientDTO(String name, int amount) {
		super();
		this.name = name;
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
