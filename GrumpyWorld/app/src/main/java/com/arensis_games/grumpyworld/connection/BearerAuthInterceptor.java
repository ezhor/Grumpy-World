package com.arensis_games.grumpyworld.connection;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dparrado on 15/02/18.
 */

public class BearerAuthInterceptor implements Interceptor {
    private String authorization;

    public BearerAuthInterceptor(String authorization) {
        this.authorization = authorization;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest  = chain.request().newBuilder()
                .addHeader("Authorization", authorization)
                .build();
        return chain.proceed(newRequest);
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
