package com.example.assignment.Models.RequestModel;

import com.example.assignment.Network.NetworkRequest;
import com.example.assignment.Utility.Constants;

public class GetApiRequest {

    public GetApiRequest(){ }

    public NetworkRequest buildAPIParameters() {
        NetworkRequest request = new NetworkRequest();
        request.setUrl(getRequestUrl());
        return request;
    }

    private String getRequestUrl(){
        return Constants.baseUrl + Constants.subUrl;
    }
}
