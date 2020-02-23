package com.example.assignment.Controller;

import android.content.Context;

import com.example.assignment.Interface.GetApiInterface;
import com.example.assignment.Models.ResponseModel.GetApiResponse;
import com.example.assignment.Network.APIBuilder;
import com.example.assignment.Network.APIInterface;
import com.example.assignment.Network.NetworkRequest;
import com.example.assignment.Network.RestClient;
import com.example.assignment.Utility.Constants;
import com.example.assignment.Utility.GlobalUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetApiDataController {

    private GetApiResponse getApiResponse;
    private GetApiInterface getApiInterface;
    private APIInterface apiInterface;

    public GetApiDataController(GetApiInterface getApiInterface) {
        this.getApiInterface = getApiInterface;
        apiInterface = RestClient.getClient().create(APIInterface.class);
    }

    public void fireGetDataApi(Context context) {
        if (GlobalUtility.checkInternetConnection(context)) {
            NetworkRequest request = APIBuilder.buildApi();

            Call call = null;
            call = apiInterface.hitApi(request.getUrl());

            call.enqueue(new Callback<GetApiResponse>() {
                @Override
                public void onResponse(Call<GetApiResponse> call, Response<GetApiResponse> response) {
                    if(response.body() != null) {
                        getApiResponse = response.body();
                        getApiInterface.getApiDataSucessfull(getApiResponse);
                    }else {
                        getApiInterface.getApiDataFailed(Constants.DEFAULT_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<GetApiResponse> call, Throwable t) {
                    getApiInterface.getApiDataFailed(Constants.DEFAULT_ERROR);
                    call.cancel();
                }
            });
        } else {
            // show no network dialog
            getApiInterface.getApiDataFailed(Constants.NO_NETWORK_ERROR);
        }
    }
}
