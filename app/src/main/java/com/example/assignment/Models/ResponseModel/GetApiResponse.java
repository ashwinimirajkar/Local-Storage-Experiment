package com.example.assignment.Models.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetApiResponse {

	@SerializedName("rankings")
	private List<RankingsItem> rankings;

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	public void setRankings(List<RankingsItem> rankings){
		this.rankings = rankings;
	}

	public List<RankingsItem> getRankings(){
		return rankings;
	}

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	@Override
 	public String toString(){
		return 
			"GetApiResponse{" + 
			"rankings = '" + rankings + '\'' + 
			",categories = '" + categories + '\'' + 
			"}";
		}
}