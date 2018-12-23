package com.rg.rahul.networklib.callback;

import org.json.JSONObject;

public interface JSONINetworkEmptyCallback extends INetworkEmptyCallback {
    void responseSuccess(JSONObject jsonResponse);
    void responseFailed(JSONObject jsonResponse);
    void responseError();
}
