package com.example.assignment.Network;

import com.example.assignment.Models.ResponseModel.GetApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIInterface {
    
    @GET
    Call<GetApiResponse> hitApi(@Url String request);
}
