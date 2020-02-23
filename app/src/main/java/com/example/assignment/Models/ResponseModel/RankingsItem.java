package com.example.assignment.Models.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankingsItem{

	@SerializedName("ranking")
	private String ranking;

	@SerializedName("products")
	private List<RankingProductItem> products;

	public void setRanking(String ranking){
		this.ranking = ranking;
	}

	public String getRanking(){
		return ranking;
	}

	public void setProducts(List<RankingProductItem> products){
		this.products = products;
	}

	public List<RankingProductItem> getProducts(){
		return products;
	}

	@Override
 	public String toString(){
		return 
			"RankingsItem{" + 
			"ranking = '" + ranking + '\'' + 
			",products = '" + products + '\'' + 
			"}";
		}
}