package com.example.finalproject_cooktutor.service;


import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClientImpl implements IHttpClient {
    OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .build();

    @Override
    public IResponse get(IRequest request) {
        /**
         *
         */
        request.setMethod(IRequest.GET);
        Map<String, String> header = request.getHeader();
        Request.Builder builder = new Request.Builder();
        for (String key : header.keySet()) {
            builder.header(key, header.get(key));
        }
        builder.url(request.getUrl()
        ).get();
        Request okRequest = builder.build();
        return execute(okRequest);
    }

    @Override
    public IResponse post(IRequest request) {
        request.setMethod(IRequest.POST);
        Map<String, Object> body = request.getBody();
        FormBody.Builder builderBody = new FormBody.Builder();
        for (String key : body.keySet()) {
            builderBody.add(key, (String) body.get(key));
        }
        RequestBody requestBody = builderBody.build();
        Map<String, String> header = request.getHeader();
        Request.Builder builder = new Request.Builder();
        for (String key : header.keySet()) {
            builder.header(key, header.get(key));
        }
        builder.url(request.getUrl())
                .post(requestBody);
        Request okRequest = builder.build();
        return execute(okRequest);
    }

    @Override
    public IResponse upload_image_post(IRequest request, Map<String, Object> map, File file) {
        /**
         *
         */
        request.setMethod(IRequest.POST);
        MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");
        MultipartBody.Builder requestBody = new MultipartBody
                .Builder().setType(MultipartBody.FORM);
        if (file != null) {
            requestBody.addFormDataPart("image", file.getName(),
                    RequestBody.create(MEDIA_TYPE_IMAGE, file));
        }
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
//                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Map<String, String> header = request.getHeader();
        Request.Builder builder = new Request.Builder();
        for (String key : header.keySet()) {
            builder.header(key, header.get(key));
        }
        builder.url(request.getUrl())
                .post(requestBody.build());
        Request okRequest = builder.build();
        return execute(okRequest);
    }

    @Override
    public IResponse delete(IRequest request) {
        request.setMethod(IRequest.DELETE);
        Map<String, String> header = request.getHeader();
        Request.Builder builder = new Request.Builder();
        for (String key : header.keySet()) {
            builder.header(key, header.get(key));
        }
        builder.url(request.getUrl())
                .delete(null);
        Request okRequest = builder.build();
        return execute(okRequest);
    }

    @Override
    public IResponse put(IRequest request) {
        request.setMethod(IRequest.PUT);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, request.getBody().toString());

        Map<String, String> header = request.getHeader();
        Request.Builder builder = new Request.Builder();
        for (String key : header.keySet()) {
            builder.header(key, header.get(key));
        }
        builder.url(request.getUrl())
                .put(requestBody);
        Request okRequest = builder.build();
        return execute(okRequest);
    }

    private IResponse execute(Request request) {
        ResponseImpl commonResponse = new ResponseImpl();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            // 设置状态码
            commonResponse.setCode(response.code());
            String body = response.body().string();
            // 设置响应数据
            commonResponse.setData(body);
        } catch (IOException e) {
            e.printStackTrace();
            commonResponse.setCode(ResponseImpl.STATE_UNKNOW_ERROR);
            commonResponse.setData(e.getMessage());
        }
        return commonResponse;
    }
}
