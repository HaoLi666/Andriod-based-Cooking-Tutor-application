package com.example.finalproject_cooktutor.service;

import java.util.Map;

public interface IRequest {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";

    /**
     *
     * @param method
     */
    void setMethod(String method);

    /**
     *
     * @param header
     */
    void setHeader(Map<String,String> header);

    /**
     *
     * @param body
     */
    void setBody(Map<String,Object> body);

    /**
     *
     * @return
     */
    String getUrl();

    /**
     *
     * @return
     */
    Map<String,String> getHeader();

    /**
     *
     * @return
     */
    Map<String,Object> getBody();
}
