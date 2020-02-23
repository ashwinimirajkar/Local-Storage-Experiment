package com.example.assignment.Network;

import com.example.assignment.Models.RequestModel.GetApiRequest;

public class APIBuilder {

    public static NetworkRequest buildApi() {
        GetApiRequest getApiRequest = new GetApiRequest();
        return getApiRequest.buildAPIParameters();
    }
}
