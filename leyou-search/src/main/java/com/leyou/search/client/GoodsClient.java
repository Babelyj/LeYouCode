package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/3/4
 * Time: 21:54
 * Description: No Description
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {



}
