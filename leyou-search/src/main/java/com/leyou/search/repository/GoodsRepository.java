package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/3/10
 * Time: 19:00
 * Description: No Description
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
