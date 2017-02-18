package com.example.malin.dictionary_app;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Path;


/**
 * Created by malin on 20-09-2016.
 */
public interface RestInterface {

    @GET("/words/{word}/definitions")
    @Headers("X-Mashape-Key : SEx2h4btKAmshMaOcdfuPisFqoKdp16iyDEjsnGKLV0pxu4sOJ")
    void sendDatainURL(@Path("word")String word, @Header("Accept") String contentType , Callback<DefResponse> callback);
}