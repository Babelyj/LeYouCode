package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author : yj
 * @date : 17:48 2020/11/23
 */
public interface BrandMapper extends Mapper<Brand> {

    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) values(#{cid}, #{bid})")
    void insertCategoryAndBrand(@Param("cid")Long cid,@Param("bid") Long bid);

    @Select("SELECT * FROM tb_brand tb LEFT JOIN tb_category_brand tcb ON tb.id =  tcb.brand_id WHERE tcb.category_id = #{cid}")
    List<Brand> selecBrandByCid(Long cid);
}
