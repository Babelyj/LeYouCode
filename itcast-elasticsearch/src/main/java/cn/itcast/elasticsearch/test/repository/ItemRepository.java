package cn.itcast.elasticsearch.test.repository;

import cn.itcast.elasticsearch.test.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/2/25
 * Time: 21:23
 * Description: No Description
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    List<Item> findByTitle(String title);

    List<Item> findByPriceBetween(Double d1, Double d2);

}
