package com.example.assignment.Models.ResponseModel;

import com.google.gson.annotations.SerializedName;

public class VariantsItem{

	@SerializedName("color")
	private String color;

	@SerializedName("size")
	private Integer size;

	@SerializedName("price")
	private Double price;

	@SerializedName("id")
	private int id;

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setSize(Integer size){
		this.size = size;
	}

	public Integer getSize(){
		return size;
	}

	public void setPrice(Double price){
		this.price = price;
	}

	public Double getPrice(){
		return price;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"VariantsItem{" + 
			"color = '" + color + '\'' + 
			",size = '" + size + '\'' + 
			",price = '" + price + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}