package com.hongpro.demo.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.swing.text.Highlighter;
import java.io.IOException;

/**
 * @Description: 文档操作
 * @Author: zhangzihong
 * @CreateTime: 2021/9/17
 * @Version:
 */
public class ESTest_Doc_Query {
    private static final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.103", 9200, "http")));
    public static void main(String[] args) throws IOException {
        searchComboDoc2();
    }

    public static void searchAllDoc() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("user");
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());
        for (SearchHit searchHit : hits) {
            System.out.println(searchHit.getSourceAsString());
        }
    }

    public static void searchConditionDoc() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.termQuery("sex", "女"));
        query.from(0);
        query.size(2);
        query.sort("sex", SortOrder.ASC);
        //数据过滤
        String[] excludes = {};
        String[] includes = {"name"};
        query.fetchSource(includes, excludes);
        request.source(query);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());
        for (SearchHit searchHit : hits) {
            System.out.println(searchHit.getSourceAsString());
        }
    }

    public static void searchComboDoc() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //多条件
        SearchSourceBuilder query = new SearchSourceBuilder();

       /* BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "男"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("tel", "10086"));
        //boolQueryBuilder.mustNot(QueryBuilders.matchQuery("tel", "10086")); //should
        query.query(boolQueryBuilder);

        //范围查询
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("tel");
        rangeQuery.gte(10086);
        rangeQuery.lte(10088);
        query.query(rangeQuery);

        //模糊查询
        FuzzyQueryBuilder fuzziness = QueryBuilders.fuzzyQuery("name", "名").fuzziness(Fuzziness.AUTO);
        query.query(fuzziness);*/

        //高亮显示
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("sex", "男");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color = 'red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("sex");
        query.highlighter(highlightBuilder);
        query.query(termQueryBuilder);

        request.source(query);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());
        for (SearchHit searchHit : hits) {
            System.out.println(searchHit.getSourceAsString());
        }
    }

    public static void searchComboDoc2() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder query = new SearchSourceBuilder();
        //最大值
        //AggregationBuilder builder = AggregationBuilders.max("maxTel").field("tel");
        //分组
        AggregationBuilder builder = AggregationBuilders.terms("sexGroup").field("sex");
        query.aggregation(builder);
        request.source(query);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());
        for (SearchHit searchHit : hits) {
            System.out.println(searchHit.getSourceAsString());
        }
    }
}
