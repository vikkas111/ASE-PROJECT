package com.example.malin.dictionary_app;

import android.content.Context;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by malin on 20-09-2016.
 */

public class RestClient implements RequestInterceptor {

    private static final String BASE_URL = "https://wordsapiv1.p.mashape.com";
    private RestInterface enforceSerVices;

    public static RestClient getInstance() {
        return new RestClient();

    }


    /**
     * Creating rest adapter
     *
     * @param context
     */
    public RestInterface getServiceSample(Context context) {
        final String basrUrl = BASE_URL;
        retrofit.client.UrlConnectionClient urlClient = new retrofit.client.UrlConnectionClient();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(basrUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL).setRequestInterceptor(this)
                .setClient(urlClient).build();
        enforceSerVices = restAdapter.create(RestInterface.class);
        return enforceSerVices;
    }


    @Override
    public void intercept(RequestFacade request) {

        //do nothing
    }
}
