package com.rg.rahul.networklib.lib;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class NetworkRunnable implements Runnable {

    private RequestType requestType;
    private String url;
    private HashMap<String, String> param;
    private String[] query;
    private long startTime;
    private long endTime;



    public abstract void beforeCreateRequest(HashMap<String, String> param);

    private static OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NetworkRunnable(RequestType requestType, String url, HashMap<String, String> param, String[] query) {
        this.requestType = requestType;
        this.url = url;
        this.param = param;
        this.query = query;
        if (client == null) {
            client = new OkHttpClient();
        }
    }

    @Override
    public void run() {
        startTime = new Date().getTime();
        execute();
    }

    private void execute() {
        beforeCreateRequest(param);
        if (requestType == RequestType.GET) {
            executeGETRequest(url, param, query);
        } else if (requestType == RequestType.POST) {
            executePOSTRequest(url, param);
        } else {
            throw new RuntimeException("request not supported");
        }
    }


    private void executeGETRequest(String url, HashMap<String, String> param, String[] query) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (query != null) {
            for (String s : query) {
                urlBuilder.addPathSegment(s);
            }
        }
        if (param != null) {
            for (String s : param.keySet()) {
                urlBuilder.addQueryParameter(s, param.get(s));
            }
        }
        String finalUrlString = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(finalUrlString)
                .build();
        exciteRequest(request);
    }

    private void executePOSTRequest(String url, HashMap<String, String> params) {
        RequestBody body = RequestBody.create(JSON, params.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        exciteRequest(request);
    }

    private void exciteRequest(Request request) {
        String responseBody = null;
        int responseCode = 0;
        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body() != null ? response.body().string() : null;
            JSONObject jsonObject = new JSONObject(responseBody);
            responseCode = response.code();
            if (response.isSuccessful()) {
                responseSuccess(jsonObject, responseBody);
            } else {
                if (responseCode == 401) {
                    int statusCode = jsonObject.getInt("status_code");
                    if (statusCode == 1006) {
                        reExciteSameRequest();
                    } else if (statusCode == 401) {
                        reExciteSameRequest();
                    } else if (statusCode == 1003) {
                        reExciteSameRequest();
                    } else {
                        responseFailed(jsonObject, responseBody);
                    }
                } else {
                    responseFailed(jsonObject, responseBody);
                }
            }
        } catch (IOException e) {
            responseError();
            e.printStackTrace();
        } catch (JSONException e) {
            responseError();
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            endTime = new Date().getTime();
            reporting(responseCode, responseBody);
        }
    }

    private void reporting(int responseCode, String responseBody) {
        Log.i("Request Time", String.valueOf(endTime - startTime));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("NetworkRunnabStri()", NetworkRunnable.this.toString());
            }
        });
        thread.start();
    }

    private void reExciteSameRequest() {
        execute();
    }

    public abstract void responseSuccess(JSONObject jsonResponse, String stringResponse);

    public abstract void responseFailed(JSONObject jsonResponse, String stringResponse);

    public abstract void responseError();

}
