package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点的id查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value="pid",defaultValue = "0")Long pid){
        List<Category> categoryList = new ArrayList<>();
        if(pid == -1){
            categoryList = this.categoryService.selectLastCategory();
        }else{
            if(null == pid || pid < 0){
                //400：参数不合法
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                return ResponseEntity.badRequest().build();

            }
            categoryList = this.categoryService.queryCategoriesByPid(pid);

            if(CollectionUtils.isEmpty(categoryList)){
                //404：资源服务器未找到
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                return ResponseEntity.notFound().build();
            }
        }


        //200：查询成功
        return ResponseEntity.ok(categoryList);

    }


    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public ResponseEntity<Category> addCategory(Category category){

        this.categoryService.addCategory(category);
        logger.info(category.toString());
        return ResponseEntity.ok(category);
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PutMapping
    public ResponseEntity<Category> editCategory(Category category){
        this.categoryService.editCategory(category);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据id删除数据
     */
    @DeleteMapping("cid/{cid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cid") Long id){
        this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
