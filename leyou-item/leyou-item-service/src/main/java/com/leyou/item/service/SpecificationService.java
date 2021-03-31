package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup record = new SpecGroup();
        record.setCid(cid);
        List<SpecGroup> groups = this.specGroupMapper.select(record);
        return groups;
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        List<SpecParam> params = this.specParamMapper.select(record);
        return params;
    }


    /**
     * 新增分组
     * @param specGroup
     */
    public void saveGroup(SpecGroup specGroup) {
        this.specGroupMapper.insertSelective(specGroup);
    }

    /**
     * 编辑分组
     * @param specGroup
     */
    public void editGroup(SpecGroup specGroup) {
        this.specGroupMapper.updateByPrimaryKeySelective(specGroup);
    }

    /**
     * 删除分组
     * @param id
     */
    public void deleteGroup(Long id) {
        //先删除分组
        this.specGroupMapper.deleteByPrimaryKey(id);
        //在删除分组对应的参数
        this.specParamMapper.deleteByGroupIdFromParam(id);
    }

    /**
     * 新增分组对应的参数
     * @param specParam
     */
    public void saveParam(SpecParam specParam) {
        this.specParamMapper.insert(specParam);
    }

    /**
     * 编辑参数
     * @param specParam
     */
    public void editParam(SpecParam specParam) {
        this.specParamMapper.updateByPrimaryKey(specParam);
    }

    /**
     * 根据id删除参数
     * @param id
     */
    public void deleteParamById(Long id) {
        this.specParamMapper.deleteByPrimaryKey(id);
    }
}
