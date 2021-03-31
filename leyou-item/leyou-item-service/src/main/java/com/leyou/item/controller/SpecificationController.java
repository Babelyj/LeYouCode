package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> groups = this.specificationService.queryGroupByCid(cid);
        /*if(CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }*/
        return  ResponseEntity.ok(groups);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(@RequestParam(value = "gid", required = false) Long gid,
                                                       @RequestParam(value = "cid", required = false)Long cid,
                                                       @RequestParam(value = "generic", required = false)Boolean generic,
                                                       @RequestParam(value = "searching", required = false)Boolean searching){
        List<SpecParam> params = this.specificationService.queryParams(gid, cid, generic, searching);
        /*if(CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }*/
        return  ResponseEntity.ok(params);
    }

    /**
     * 新增分组
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(SpecGroup specGroup){
        this.specificationService.saveGroup(specGroup);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 编辑分组
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> editGroup(SpecGroup specGroup){
        this.specificationService.editGroup(specGroup);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id){
        this.specificationService.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 新增分组对应的参数
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveParam(SpecParam specParam){
        this.specificationService.saveParam(specParam);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 编辑参数
     * @param specParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> editParam(SpecParam specParam){
        this.specificationService.editParam(specParam);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据id删除参数
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParamById(@PathVariable("id") Long id){
        this.specificationService.deleteParamById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
