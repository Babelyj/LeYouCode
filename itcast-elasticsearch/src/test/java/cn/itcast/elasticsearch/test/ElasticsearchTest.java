package cn.itcast.elasticsearch.test;

import cn.itcast.elasticsearch.test.pojo.Item;
import cn.itcast.elasticsearch.test.repository.ItemRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/2/24
 * Time: 21:24
 * Description: No Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testIndex(){
        this.elasticsearchTemplate.createIndex(Item.class);
        this.elasticsearchTemplate.putMapping(Item.class);
        //删除索引库
        this.elasticsearchTemplate.deleteIndex(Item.class);
    }

    @Test
    public void testCreate(){
        Item item = new Item(1L, "小米手机7", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        //this.itemRepository.save(item);

        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R2", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        // 接收对象集合，实现批量新增
        this.itemRepository.saveAll(list);
    }

    @Test
    public void testFind(){
        //Optional<Item> item = this.itemRepository.findById(1L);

        Iterable<Item> items = this.itemRepository.findAll(Sort.by("price").descending());
        items.forEach(System.out::println);
        //System.out.println(item.get());

    }

    @Test
    public void testFindByTitle(){
        //List<Item> items = this.itemRepository.findByTitle("手机");
        List<Item> items = this.itemRepository.findByPriceBetween(3699d, 4499d);
        items.forEach(System.out::println);
    }

    @Test
    public void indexList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }


    @Test
    public void testSearch(){
        //通过查询构建起工具构建查询条件
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "手机");
        //执行查询，获取结果集
        Iterable<Item> search = this.itemRepository.search(matchQueryBuilder);
        search.forEach(System.out::println);
    }


    @Test
    public void testNative(){
        //构建自定义查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //添加基本的查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));
        //执行查询获取分页结果集
        Page<Item> search = this.itemRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(search.getTotalPages());
        System.out.println(search.getTotalElements());
        search.forEach(System.out::println);

    }

    @Test
    public void testPage(){
        //构建自定义查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //添加基本的查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("category", "手机"));
        //添加分页条件，页码从0开始
        //nativeSearchQueryBuilder.withPageable(PageRequest.of(1,2));
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        //执行查询获取分页结果集
        Page<Item> search = this.itemRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(search.getTotalPages());
        System.out.println(search.getTotalElements());
        search.forEach(System.out::println);
    }

    @Test
    public void testAggs(){

        //初始化自定义查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //添加聚合
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand"));
        //添加结果集过滤，不包括任何字段
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        //执行聚合查询
        AggregatedPage<Item> itemPage = (AggregatedPage<Item>)this.itemRepository.search(nativeSearchQueryBuilder.build());
        //解析聚合结果集,
        // 根据聚合的类型以及字段类型要进行强转，
        // brand-是字符串类型的，
        // 聚合类型-词条聚合，
        // brandAgg-通过聚合名称获取聚合对象
        StringTerms brandAgg = (StringTerms)itemPage.getAggregation("brandAgg");
        //获取桶的集合
        List<StringTerms.Bucket> buckets = brandAgg.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        });


    }


    @Test
    public void testSubAggs(){

        //初始化自定义查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //添加聚合
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand")
                .subAggregation(AggregationBuilders.avg("priceAvg").field("price")));
        //添加结果集过滤，不包括任何字段
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        //执行聚合查询
        AggregatedPage<Item> itemPage = (AggregatedPage<Item>)this.itemRepository.search(nativeSearchQueryBuilder.build());
        //解析聚合结果集,
        // 根据聚合的类型以及字段类型要进行强转，
        // brand-是字符串类型的，
        // 聚合类型-词条聚合，
        // brandAgg-通过聚合名称获取聚合对象
        StringTerms brandAgg = (StringTerms)itemPage.getAggregation("brandAgg");
        //获取桶的集合
        List<StringTerms.Bucket> buckets = brandAgg.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
            //System.out.println(bucket.getAggregations());
            //获取子聚合的map集合：key-聚合名称，value-对应的子聚合对象
            Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
            InternalAvg priceAvg = (InternalAvg)stringAggregationMap.get("priceAvg");
            System.out.println(priceAvg.getValue());
        });

    }




}