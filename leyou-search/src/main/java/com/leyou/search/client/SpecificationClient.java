package com.leyou.search.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/3/4
 * Time: 22:40
 * Description: No Description
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
