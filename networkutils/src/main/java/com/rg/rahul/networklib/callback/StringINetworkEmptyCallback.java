package com.rg.rahul.networklib.callback;

public interface StringINetworkEmptyCallback extends INetworkEmptyCallback {
    void responseSuccess(String jsonResponse);

    void responseFailed(String jsonResponse);

    void responseError();
}
