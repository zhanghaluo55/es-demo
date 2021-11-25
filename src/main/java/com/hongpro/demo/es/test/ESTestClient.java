package com.hongpro.demo.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author zhangzihong
 * @version 1.0.0.0
 * @description
 * @date 2021/5/19 18:09
 */
public class ESTestClient {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.103", 9200, "http")));
        client.close();
    }
}
