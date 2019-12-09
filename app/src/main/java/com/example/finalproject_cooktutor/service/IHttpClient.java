package com.example.finalproject_cooktutor.service;

import java.io.File;
import java.util.Map;

public interface IHttpClient {
    IResponse get(IRequest request);

    /**
     *
     * @param request
     * @return
     */
    IResponse post(IRequest request);

    /**
     *
     * @param request
     * @param map
     * @param file
     * @return
     */
    IResponse upload_image_post(IRequest request, Map<String, Object> map, File file);
    IResponse delete(IRequest request);
    IResponse put(IRequest request);
}
