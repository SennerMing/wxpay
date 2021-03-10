package com.musician.wxpay.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.musician.wxpay.service.WebsiteInfoService;
import com.musician.wxpay.service.WebsiteTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LXR
 * @since: 2021/1/28 15:00
 */
@Service
@Slf4j
public class WebsiteTypeServiceImpl implements WebsiteTypeService {

    @Autowired
    private WebsiteTypeMapper websiteTypeMapper;

    @Autowired
    private WebsiteInfoService websiteInfoService;

    @Override
    public List<OrgWebsiteInfo> packageAllWebsiteInfosByOrgId(int orgId) {
        List<OrgWebsiteInfo> orgWebsiteInfos = new ArrayList<>(20);
        LambdaQueryWrapper<WebsiteType> queryWrapper = new QueryWrapper<WebsiteType>().lambda()
                .eq(WebsiteType::getOrgId, orgId);
        List<WebsiteType> websiteTypes = websiteTypeMapper.selectList(queryWrapper);

        for(WebsiteType websiteType : websiteTypes){
            List<WebsiteInfo> websiteInfos = websiteInfoService.packageWebsiteInfosByType(websiteType.getId());

            OrgWebsiteInfo orgWebsiteInfo = OrgWebsiteInfo.builder()
                    .websiteType(websiteType).websiteInfos(websiteInfos).build();
            orgWebsiteInfos.add(orgWebsiteInfo);
        }
        return orgWebsiteInfos;
    }

    @Override
    public OrgWebsiteInfo packageAllWebsitesInfoByType(int typeId) {
        WebsiteType websiteType = websiteTypeMapper.selectById(typeId);
        List<WebsiteInfo> websiteInfos = websiteInfoService.packageWebsiteInfosByType(websiteType.getId());
        return OrgWebsiteInfo.builder().websiteType(websiteType)
                .websiteInfos(websiteInfos).build();
    }

    @Override
    public List<OrgWebsiteInfo> packageAllWebsiteInfo(int orgId, String keyword) {
        List<OrgWebsiteInfo> orgWebsiteInfos = new ArrayList<>(20);
        keyword = keyword.trim();
        List<WebsiteInfo> websiteInfos = websiteInfoService.packageAllWebsiteInfos(keyword);
        for (WebsiteInfo websiteInfo : websiteInfos) {
            orgWebsiteInfos.add(packageAllWebsitesInfoByType(websiteInfo.getTypeId()));
        }
        return orgWebsiteInfos;
    }

    @Override
    public int newWebsiteType(WebsiteType websiteType) {
        websiteTypeMapper.insert(websiteType);
        return websiteType.getId();
    }

    @Override
    public List<WebsiteType> getWebsiteTypeByOrgId(int orgId) {
        LambdaQueryWrapper<WebsiteType> queryWrapper = new QueryWrapper<WebsiteType>().lambda()
                .eq(WebsiteType::getOrgId, orgId);
        return websiteTypeMapper.selectList(queryWrapper);
    }

    @Override
    public int renameWebsiteType(WebsiteType websiteType) {
        Wrapper<WebsiteType> sensitiveTypeWrapper = Wrappers.<WebsiteType>lambdaUpdate()
                .set(WebsiteType::getTypeName, websiteType.getTypeName()).eq(WebsiteType::getId, websiteType.getId());
        return websiteTypeMapper.update(null, sensitiveTypeWrapper);
    }

    @Override
    public int removeWebsiteType(int id) {
        List<WebsiteInfo> websiteInfos = websiteInfoService.packageWebsiteInfosByType(id);
        if(websiteInfos.size() > 0){
            log.info("该分类不为空，请先删除其中站点信息或将其合并到其他分类");
            return -1;
        }else{
            return websiteTypeMapper.deleteById(id);
        }
    }

    @Override
    public int removeWebsiteTypes(List<Integer> websiteTypeIds) {
        boolean hasWebsiteInfos = websiteInfoService.existWebsiteInfoTypeId(websiteTypeIds);
        if(hasWebsiteInfos){
            log.info("该分类列表存在不为空项，请先删除其中站点或将其合并到其他分类");
            return -1;
        }else{
            return websiteTypeMapper.deleteBatchIds(websiteTypeIds);
        }
    }

    @Override
    public int combineWebsiteInfo(int removeTypeId, int targetTypeId) {
        //***************************合并可能存在相同关键词的情况！***************************
        int wordsChange = websiteInfoService.changeWebsiteInfoTypeIdTo(removeTypeId, targetTypeId);
        int typeRemove = this.removeWebsiteType(removeTypeId);
        int duplicateCount = websiteInfoService.distinctWebsiteInfo(targetTypeId);
        int duplicateRemoval = websiteInfoService.duplicateRemoval(targetTypeId);
        log.info("合并去重了["+duplicateRemoval+"]条数据！");
        return duplicateCount;
    }
}
