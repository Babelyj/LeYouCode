package com.leyou.item.mapper;

import com.leyou.item.pojo.SpecParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SpecParamMapper extends Mapper<SpecParam> {


    @Delete("delete from tb_spec_param where group_id = #{id}")
    void deleteByGroupIdFromParam(@Param("id") Long id);
}
