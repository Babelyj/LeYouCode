package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 根據父節點查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categoryList = this.categoryMapper.select(category);
        return categoryList;
    }

    /**
     * 新增分类
     * @param category
     */
    public void addCategory(Category category) {
        /**
         * 将本节点新增入数据库，id设置未null
         * 并且将此节点的父节点的isParent设置未true
         */
        //将id设置为null
        category.setId(null);
        this.categoryMapper.insertSelective(category);
        //修改父节点
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(parent);
    }

    public void deleteCategory(Long id) {
        Category category = this.categoryMapper.selectByPrimaryKey(id);
        if(category.getIsParent()){
            //若为非叶子节点

            //1.查找所有的叶子节点
            List<Category> leafList = new ArrayList<>();
            //queryAllLeafNode(category, leafList);

            //2.查找所有的子节点
        }
    }
}
