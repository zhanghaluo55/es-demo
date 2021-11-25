package com.hongpro.demo.es.test;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: zhangzihong
 * @CreateTime: 2021/9/23
 * @Version:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESIndexTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private ProductDao productDao;

    @Test
    public void createIndex() {
        System.out.println("创建索引");
    }

    @Test
    public void deleteIndex() {
        String delete = elasticsearchRestTemplate.delete(Product.class);
        System.out.println("result = " + delete);
    }

    @Test
    public void save() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("小玩具");
        product.setCategory("玩具");
        product.setPrice(2999.0);
        product.setImages("https://www.baidu.com");
        productDao.save(product);

        Product product1 = productDao.findById(2L).get();
        //findAll、delete、saveAll
    }

    @Test
    public void findByPageable() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int curPage = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(curPage, pageSize, sort);
        Page<Product> pages = productDao.findAll(pageRequest);
        for (Product product : pages.getContent()) {
            System.out.println(product);
        }
    }

    @Test
    public void termQuery() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int curPage = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(curPage, pageSize, sort);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
        Iterable<Product> search = productDao.search(termQueryBuilder, pageRequest);
        for (Product product : search) {
            System.out.println(product);
        }
    }

}
