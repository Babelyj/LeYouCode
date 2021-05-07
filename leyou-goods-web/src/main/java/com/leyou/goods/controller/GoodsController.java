package com.leyou.goods.controller;

import com.leyou.goods.service.GoodsHtmlService;
import com.leyou.goods.service.GoodsService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
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

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    @GetMapping("item/{spuId}.html")
    public String toItemPage(@PathVariable("spuId")Long id, Model model){
        Map<String, Object> map = this.goodsService.loadData(id);

        model.addAllAttributes(map);

        this.goodsHtmlService.createHtml(id);

        return "item";
    }

}