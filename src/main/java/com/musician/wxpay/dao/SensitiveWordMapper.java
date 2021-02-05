package com.musician.wxpay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.musician.wxpay.entity.SensitiveWord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author: LXR
 * @since: 2021/1/28 14:24
 */
@Mapper
@Repository
public interface SensitiveWordMapper extends BaseMapper<SensitiveWord> {

    @Delete("delete from tab_sensitive_word where id in (select id from (select id from tab_sensitive_word where sensitive_word in " +
            "(select sensitive_word from tab_sensitive_word where type_id = #{type_id} group by sensitive_word having count(sensitive_word)>1) and id not in" +
            "(select min(id) from tab_sensitive_word where type_id = #{type_id} group by sensitive_word having count(sensitive_word)>1)) as tmpresult)")
    int duplicateRemoval(int typeId);
}
