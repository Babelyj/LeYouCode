package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author : yj
 * @date : 17:49 2020/11/23
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;


    public PageResult<Brand> queryBrandsByPage(String key, Integer page,Integer rows, String sortBy, Boolean desc) {
        //初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //根据name模糊查询，或者根据首字母查询
        if (!StringUtils.isEmpty(key)){
            //property为表字段
            criteria.andLike("name", "%"+key+"%").orLike("letter",key);

        }

        //添加分页条件
        PageHelper.startPage(page,rows);

        //添加排序条件
        if(!StringUtils.isEmpty(sortBy)){
            example.setOrderByClause(sortBy + " " + (desc? "desc":"asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);

        //包装成pageInfo
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        //包装成分页结果集
        return new PageResult<>(brandPageInfo.getTotal(),brandPageInfo.getList());
    }
}
