package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/3/4
 * Time: 22:39
 * Description: No Description
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
