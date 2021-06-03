package com.bhavaniprasad.viewandselect.remote;

import com.bhavaniprasad.viewandselect.model.features;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.ArrayList;
import java.util.List;

public interface ApiService {
    @GET("/mhrpatel12/esper-assignment/db")
    Call<features> getList();
}






