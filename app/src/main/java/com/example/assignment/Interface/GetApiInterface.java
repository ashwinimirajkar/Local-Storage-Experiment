package com.example.assignment.Interface;

import com.example.assignment.Models.ResponseModel.GetApiResponse;

public interface GetApiInterface {

    public void getApiDataSucessfull(GetApiResponse data);
    public void getApiDataFailed(String error);
}
