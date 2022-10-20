package com.zitian.csims.network;

import com.yolanda.nohttp.rest.Response;

public interface HttpListener<T> {
    public void onSucceed(int what, Response<T> response);
    public void onFailed(int what, Response<T> response);
}
