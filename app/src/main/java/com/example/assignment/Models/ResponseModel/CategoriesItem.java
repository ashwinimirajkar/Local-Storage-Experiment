package com.example.assignment.Models.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("child_categories")
	private List<Integer> childCategories;

	@SerializedName("products")
	private List<ProductsItem> products;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setChildCategories(List<Integer> childCategories){
		this.childCategories = childCategories;
	}

	public List<Integer> getChildCategories(){
		return childCategories;
	}

	public void setProducts(List<ProductsItem> products){
		this.products = products;
	}

	public List<ProductsItem> getProducts(){
		return products;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",child_categories = '" + childCategories + '\'' + 
			",products = '" + products + '\'' + 
			"}";
		}
}