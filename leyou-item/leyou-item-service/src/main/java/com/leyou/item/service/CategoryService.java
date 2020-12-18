package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 根据父节点查询子节点
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
        //category.setId(null);
        this.categoryMapper.insertSelective(category);
        //修改父节点
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(parent);
    }

    /**
     * 此处逻辑稍微有些复杂
     * 1.若删除的节点为父节点：
     *      查询所有的叶子节点：用于维护中间表
     *      查询所有的子节点包括自己：用于删除
     * 2.若删除的节点不为父节点:
     *      查看是否有兄弟：有则直接删除即可，
     *                     否则删除后将其父节点变为叶子节点
     * @param id
     */
    public void deleteCategory(Long id) {
        Category category = this.categoryMapper.selectByPrimaryKey(id);
        if(category.getIsParent()){
            //若为非叶子节点
            //1.查找所有的叶子节点
            List<Category> leafList = new ArrayList<>();
            List<Category> nodeList = new ArrayList<>();
            queryAllLeafNode(category, leafList, nodeList);

            //删除tb_category中的数据
            for (Category ca : nodeList){
                this.categoryMapper.delete(ca);
            }

            //维护中间表
            for (Category ca : leafList){
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(ca.getId());
            }
        }else{
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("parentId",category.getParentId());
            List<Category> list = this.categoryMapper.selectByExample(example);
            if(list.size() == 1){
                Category parent = new Category();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                this.categoryMapper.updateByPrimaryKeySelective(parent);
            }
            //不管有没有兄弟都要删除自身
            this.categoryMapper.deleteByPrimaryKey(category.getId());

            //维护中间表
            this.categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
        }
    }

    /**
     * 获取叶子节点
     * @param category
     * @param leafList
     */
    private void queryAllLeafNode(Category category, List<Category> leafList, List<Category> nodeList) {
        if(!category.getIsParent()){
            //叶子节点
            leafList.add(category);
        }

        //所有子节点
        nodeList.add(category);

        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId", category.getId());
        List<Category> list = this.categoryMapper.selectByExample(example);

        for (Category ca : list){
            queryAllLeafNode(ca, leafList, nodeList);
        }

    }

    /**
     * 根据id修改分类
     * @param category
     */
    public void editCategory(Category category) {
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
     * 查找最新创建的分类信息
     * @return
     */
    public List<Category> selectLastCategory() {
        List<Category> list = new ArrayList<>();
        list = this.categoryMapper.selectLastCategory();
        //防止第一次进行分类添加
        if(CollectionUtils.isEmpty(list)){
            list.add(new Category(Long.valueOf(0)));
        }
        return list;

    }
}
