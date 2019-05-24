package com.network;

/**
 * Created by kris.
 * 支持8 种请求方式
 * GET，HEAD，OPTIONS，POST，PUT，DELETE，PATCH
 *
 * 常用 Get Post
 */
public class RequestType {

    /**
     * 默认使用POST，如果要使用GET，在请求参数里添加参数"RequestMethod":"GET"
     * Task里已经过滤了RequestMethod参数
     *
     */
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    //post json
    public static final String POSTJSON = "POSTJSON";
}
