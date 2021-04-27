package com.leyou.goods.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

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
