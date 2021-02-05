package com.musician.wxpay.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.musician.wxpay.entity.WebsiteInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: LXR
 * @since: 2021/1/28 14:24
 */
@Mapper
@Repository
public interface WebsiteInfoMapper extends BaseMapper<WebsiteInfo> {

    @Delete("delete from tab_website_info where (site_name,site_url) in " +
            "(select t1.site_name,t1.site_url from " +
            "(select site_name,site_url from tab_website_info where type_id = #{type_id} group by site_name,site_url having count(*) > 1) t1) " +
            "and id not in " +
            "(select t2.minid from (select min(id) minid from tab_website_info where type_id = #{type_id} group by site_name,site_url having count(*) > 1) t2)")
    int duplicateRemoval(int typeId);

}
