package com.hongpro.demo.es.test;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: zhangzihong
 * @CreateTime: 2021/9/23
 * @Version:
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}
