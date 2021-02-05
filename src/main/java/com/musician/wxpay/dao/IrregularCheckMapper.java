package com.musician.wxpay.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.musician.wxpay.entity.IrregularCheck;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author: LXR
 * @since: 2021年2月2日 13点36分
 */
@Mapper
@Repository
public interface IrregularCheckMapper extends BaseMapper<IrregularCheck> {

    @Select("select * from tab_irregular_check ${ew.customSqlSegment}")
    IPage<IrregularCheck> selectIrregularPage(Page<IrregularCheck> page, @Param(Constants.WRAPPER) Wrapper<IrregularCheck> wrapper);

}
