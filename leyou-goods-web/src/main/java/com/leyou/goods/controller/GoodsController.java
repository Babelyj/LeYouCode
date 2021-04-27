package com.leyou.goods.controller;

import com.leyou.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/4/19
 * Time: 21:13
 * Description: No Description
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("item/{spuId}.html")
    public String toItemPage(@PathVariable("spuId")Long id, Model model){
        Map<String, Object> map = this.goodsService.loadData(id);

        model.addAllAttributes(map);

        return "item";
    }
}