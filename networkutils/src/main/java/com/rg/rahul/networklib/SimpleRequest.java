package com.rg.rahul.networklib;

import com.rg.rahul.networklib.callback.INetworkEmptyCallback;
import com.rg.rahul.networklib.callback.JSONINetworkEmptyCallback;
import com.rg.rahul.networklib.callback.StringINetworkEmptyCallback;
import com.rg.rahul.networklib.lib.NetworkRunnable;
import com.rg.rahul.networklib.lib.RequestType;

import org.json.JSONObject;

import java.util.HashMap;

public class SimpleRequest extends NetworkRunnable {

    private INetworkEmptyCallback responseCallback = null;

    public SimpleRequest(RequestType requestType, String url, HashMap<String, String> param, String... query) {
        super(requestType, url, param, query);
    }

    @Override
    public void responseSuccess(JSONObject jsonResponse, String stringResponse) {
        if (responseCallback == null) {

        } else if (responseCallback instanceof JSONINetworkEmptyCallback) {
            ((JSONINetworkEmptyCallback) responseCallback).responseSuccess(jsonResponse);
        } else if (responseCallback instanceof StringINetworkEmptyCallback) {
            ((StringINetworkEmptyCallback) responseCallback).responseSuccess(stringResponse);
        }
    }

    @Override
    public void responseFailed(JSONObject jsonResponse, String stringResponse) {
        if (responseCallback == null) {

        } else if (responseCallback instanceof JSONINetworkEmptyCallback) {
            ((JSONINetworkEmptyCallback) responseCallback).responseFailed(jsonResponse);
        } else if (responseCallback instanceof StringINetworkEmptyCallback) {
            ((StringINetworkEmptyCallback) responseCallback).responseFailed(stringResponse);
        }
    }

    @Override
    public void responseError() {
        if (responseCallback == null) {

        } else if (responseCallback instanceof JSONINetworkEmptyCallback) {
            ((JSONINetworkEmptyCallback) responseCallback).responseError();
        } else if (responseCallback instanceof StringINetworkEmptyCallback) {
            ((StringINetworkEmptyCallback) responseCallback).responseError();
        }
    }

    @Override
    public void beforeCreateRequest(HashMap<String, String> param) {

    }

    public void setResponseCallback(JSONINetworkEmptyCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

    public void setResponseCallback(StringINetworkEmptyCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

}
