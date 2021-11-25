package com.hongpro.demo.es.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Description:
 * @Author: zhangzihong
 * @CreateTime: 2021/9/23
 * @Version:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Document(indexName = "product", shards = 3, replicas = 1)
public class Product {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Keyword, index = false)
    private String images;
}
