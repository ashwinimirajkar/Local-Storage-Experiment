package com.example.assignment.Models.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsItem{

	@SerializedName("date_added")
	private String dateAdded;

	@SerializedName("name")
	private String name;

	@SerializedName("tax")
	private Tax tax;

	@SerializedName("id")
	private int id;

	@SerializedName("variants")
	private List<VariantsItem> variants;

	public void setDateAdded(String dateAdded){
		this.dateAdded = dateAdded;
	}

	public String getDateAdded(){
		return dateAdded;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTax(Tax tax){
		this.tax = tax;
	}

	public Tax getTax(){
		return tax;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setVariants(List<VariantsItem> variants){
		this.variants = variants;
	}

	public List<VariantsItem> getVariants(){
		return variants;
	}

	@Override
 	public String toString(){
		return 
			"ProductsItem{" + 
			"date_added = '" + dateAdded + '\'' + 
			",name = '" + name + '\'' + 
			",tax = '" + tax + '\'' + 
			",id = '" + id + '\'' + 
			",variants = '" + variants + '\'' + 
			"}";
		}
}