package com.hongpro.demo.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @Description: 文档操作
 * @Author: zhangzihong
 * @CreateTime: 2021/9/17
 * @Version:
 */
public class ESTest_Doc_Modify {
    private static final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.103", 9200, "http")));
    public static void main(String[] args) throws IOException {
        batchInsertDoc();
    }

    public static void insertDoc() throws IOException {
        //创建doc
        IndexRequest request = new IndexRequest();
        request.index("user").id("1002");

        User user = User.builder().name("小名").tel("1008611").sex("男").build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        request.source(json, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    public static void batchInsertDoc() throws IOException {
        //创建doc
        BulkRequest request = new BulkRequest();
        //new DeleteRequest().index("user).id("1003) 删除
        request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "小三", "sex", "男"));
        request.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "小四", "sex", "女"));
        request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "小五", "sex", "女"));
        request.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "小六", "sex", "女"));
        request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "小七", "sex", "女"));

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        System.out.println(response.getItems());
    }

    public static void updateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");
        request.doc(XContentType.JSON, "sex", "女");
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    public static void getDoc() throws IOException {
        GetRequest request = new GetRequest();
        request.index("user").id("1001");
        GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
    }

    public static void deleteDoc() throws IOException {
        //创建索引
        DeleteRequest request = new DeleteRequest();
        request.index("user");
        DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.getResult());
    }
}
