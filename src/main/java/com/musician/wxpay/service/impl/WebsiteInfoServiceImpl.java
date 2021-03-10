package com.musician.wxpay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.musician.wxpay.service.WebsiteInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: LXR
 * @since: 2021/1/29 10:47
 */
@Service
@Slf4j
public class WebsiteInfoServiceImpl implements WebsiteInfoService {

    @Autowired
    private WebsiteInfoMapper websiteInfoMapper;


    @Override
    public List<WebsiteInfo> packageWebsiteInfosByType(int typeId) {
        LambdaQueryWrapper<WebsiteInfo> queryWrapper = new QueryWrapper<WebsiteInfo>().lambda()
                .eq(WebsiteInfo::getTypeId, typeId);
        List<WebsiteInfo> websiteInfos = websiteInfoMapper.selectList(queryWrapper);
        return websiteInfos;
    }

    @Override
    public List<WebsiteInfo> packageAllWebsiteInfos(String keyword) {
        LambdaQueryWrapper<WebsiteInfo> queryWrapper = new QueryWrapper<WebsiteInfo>().lambda()
                .like(WebsiteInfo::getSiteName, keyword).or().like(WebsiteInfo::getSiteUrl, keyword).groupBy(WebsiteInfo::getTypeId);
        List<WebsiteInfo> websiteInfos = websiteInfoMapper.selectList(queryWrapper);
        return websiteInfos;
    }

    @Override
    public int newWebsiteInfo(WebsiteInfo websiteInfo) {
        LambdaQueryWrapper<WebsiteInfo> queryWrapper = new QueryWrapper<WebsiteInfo>().lambda()
                .eq(WebsiteInfo::getTypeId, websiteInfo.getTypeId())
                .eq(WebsiteInfo::getSiteName, websiteInfo.getSiteName())
                .eq(WebsiteInfo::getSiteUrl, websiteInfo.getSiteUrl());
        int count = websiteInfoMapper.selectCount(queryWrapper);
        if(count > 0){
            log.info("已存在");
            return -1;
        }else{
            websiteInfoMapper.insert(websiteInfo);
        }
        return websiteInfo.getId();
    }

    @Override
    public int removeWebsiteInfo(int websiteId) {
        return websiteInfoMapper.deleteById(websiteId);
    }

    @Override
    public int removeWebsiteInfos(List<Integer> websiteTypeIds) {
        return websiteInfoMapper.deleteBatchIds(websiteTypeIds);
    }

    @Override
    public boolean existWebsiteInfoTypeId(List<Integer> websiteTypeIds) {
        LambdaQueryWrapper<WebsiteInfo> queryWrapper = new QueryWrapper<WebsiteInfo>().lambda()
                .in(WebsiteInfo::getTypeId, websiteTypeIds);
        return websiteInfoMapper.selectCount(queryWrapper) > 0 ? true : false;
    }

    @Override
    public int changeWebsiteInfoTypeIdTo(int originTypeId, int newTypeId) {
        return websiteInfoMapper.update(null, Wrappers.<WebsiteInfo>lambdaUpdate().set(WebsiteInfo::getTypeId, newTypeId)
                .eq(WebsiteInfo::getTypeId, originTypeId));
    }

    @Override
    public int distinctWebsiteInfo(int typeId) {
        QueryWrapper<WebsiteInfo> queryWrapper = new QueryWrapper<WebsiteInfo>();
        queryWrapper.select("site_name,count(site_name) as count").groupBy("site_name");
        List<WebsiteInfo> websiteInfos = websiteInfoMapper.selectList(queryWrapper);
        int count = 0;
        for(int i = 0;i < websiteInfos.size();i++){
            if(websiteInfos.get(i).getCount()>1){
                count++;
            }
        }
        return count;
    }


    @Override
    public int duplicateRemoval(int typeId) {
        return websiteInfoMapper.duplicateRemoval(typeId);
    }
}
