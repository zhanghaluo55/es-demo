package com.hongpro.demo.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * @Description: 索引操作
 * @Author: zhangzihong
 * @CreateTime: 2021/9/17
 * @Version:
 */
public class ESTest_Index_Modify {
    private static final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.103", 9200, "http")));
    public static void main(String[] args) throws IOException {
        getIndex();
    }

    public static void createIndex() throws IOException {
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("game");
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println("result:" + acknowledged);
    }

    public static void getIndex() throws IOException {
        //创建索引
        GetIndexRequest request = new GetIndexRequest("game");
        GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());
    }

    public static void deleteIndex() throws IOException {
        //创建索引
        DeleteIndexRequest request = new DeleteIndexRequest("game");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }
}
