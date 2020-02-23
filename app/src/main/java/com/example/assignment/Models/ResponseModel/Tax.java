package com.example.assignment.Models.ResponseModel;

import com.google.gson.annotations.SerializedName;

public class Tax{

	@SerializedName("name")
	private String name;

	@SerializedName("value")
	private double value;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setValue(double value){
		this.value = value;
	}

	public double getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"Tax{" + 
			"name = '" + name + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}