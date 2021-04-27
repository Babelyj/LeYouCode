package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("spec")
public interface SpecificationApi {

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public List<SpecParam> queryParams(@RequestParam(value = "gid", required = false) Long gid,
                                                       @RequestParam(value = "cid", required = false)Long cid,
                                                       @RequestParam(value = "generic", required = false)Boolean generic,
                                                       @RequestParam(value = "searching", required = false)Boolean searching);


    @GetMapping("group/param/{cid}")
    public List<SpecGroup> queryGroupsWithParam(@PathVariable("cid")Long cid);

}
