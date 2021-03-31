package com.leyou.item.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("category")
public interface CategoryApi {

    @GetMapping
    public List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);



}
