package com.musician.wxpay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.musician.wxpay.entity.SensitiveType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: LXR
 * @since: 2021/1/28 14:24
 */
@Mapper
@Repository
public interface SensitiveTypeMapper extends BaseMapper<SensitiveType> {
}
