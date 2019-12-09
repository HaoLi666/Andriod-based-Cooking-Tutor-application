package com.example.finalproject_cooktutor.service;

import java.util.Map;

public class RequestImpl implements IRequest {
    private String method = POST;
    private String url;
    private Map<String, String> header;
    private Map<String, Object> body;

    public RequestImpl(String url) {
        this.url = url;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    @Override
    public void setBody(Map<String, Object> body) {
        this.body = body;
    }


    @Override
    public String getUrl() {
        if (GET.equals(method)) {
            for (String key : body.keySet()) {
                url = url.replace("${" + key + "}", body.get(key).toString());
            }
        }
        return url;
    }

    @Override
    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public Map<String,Object> getBody() {
        return body;
    }

}
