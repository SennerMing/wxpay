package com.musician.wxpay.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.musician.wxpay.service.SensitiveTypeService;
import com.musician.wxpay.service.SensitiveWordService;
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
public class SensitiveTypeServiceImpl implements SensitiveTypeService {

    @Autowired
    private SensitiveTypeMapper sensitiveTypeMapper;

    @Autowired
    private SensitiveWordService sensitiveWordService;


    @Override
    public List<OrgSensitiveInfo> packageAllSensitiveWordsByOrgId(int orgId) {
        List<OrgSensitiveInfo> orgSensitiveInfos = new ArrayList<>(20);
        LambdaQueryWrapper<SensitiveType> queryWrapper = new QueryWrapper<SensitiveType>().lambda()
                .eq(SensitiveType::getOrgId, orgId);
        List<SensitiveType> sensitiveTypeList = sensitiveTypeMapper.selectList(queryWrapper);

        for(SensitiveType sensitiveType : sensitiveTypeList){
            List<SensitiveWord> sensitiveWordList = sensitiveWordService.packageSensitiveWordsByType(sensitiveType.getId());
            OrgSensitiveInfo orgSensitiveInfo = OrgSensitiveInfo.builder().sensitiveType(sensitiveType)
                    .sensitiveWordList(sensitiveWordList).build();
            orgSensitiveInfos.add(orgSensitiveInfo);
        }
        return orgSensitiveInfos;
    }

    @Override
    public OrgSensitiveInfo packageAllSensitiveWordsByTypeId(int typeId) {
        SensitiveType sensitiveType = sensitiveTypeMapper.selectById(typeId);
        List<SensitiveWord> sensitiveWordList = sensitiveWordService.packageSensitiveWordsByType(sensitiveType.getId());
        return OrgSensitiveInfo.builder().sensitiveType(sensitiveType)
                .sensitiveWordList(sensitiveWordList).build();
    }

    @Override
    public List<OrgSensitiveInfo> packageAllSensitiveInfo(int orgId, String keyword) {
        List<OrgSensitiveInfo> orgSensitiveInfos = new ArrayList<>(20);
        keyword = keyword.trim();
        List<SensitiveWord> sensitiveWordList = sensitiveWordService.packageAllSensitiveWords(keyword);
        for (SensitiveWord sensitiveWord : sensitiveWordList) {
            orgSensitiveInfos.add(packageAllSensitiveWordsByTypeId(sensitiveWord.getTypeId()));
        }
        return orgSensitiveInfos;
    }

    @Override
    public int newSensitiveType(SensitiveType sensitiveType) {
        sensitiveTypeMapper.insert(sensitiveType);
        return sensitiveType.getId();
    }

    @Override
    public List<SensitiveType> getSensitiveTypeByOrgId(int orgId) {
        LambdaQueryWrapper<SensitiveType> queryWrapper = new QueryWrapper<SensitiveType>().lambda()
                .eq(SensitiveType::getOrgId, orgId);
        return sensitiveTypeMapper.selectList(queryWrapper);
    }

    @Override
    public int renameSensitiveType(SensitiveType sensitiveType) {
        Wrapper<SensitiveType> sensitiveTypeWrapper = Wrappers.<SensitiveType>lambdaUpdate()
                .set(SensitiveType::getTypeName, sensitiveType.getTypeName()).eq(SensitiveType::getId, sensitiveType.getId());
        return sensitiveTypeMapper.update(null, sensitiveTypeWrapper);
    }

    @Override
    public int removeSensitiveType(int sensitiveTypeId) {
        List<SensitiveWord> sensitiveWordList = sensitiveWordService.packageSensitiveWordsByType(sensitiveTypeId);
        if(sensitiveWordList.size() > 0){
            log.info("该敏感词分类不为空，请先删除其中敏感词或将其合并到其他分类");
            return -1;
        }else{
            return sensitiveTypeMapper.deleteById(sensitiveTypeId);
        }
    }

    @Override
    public int removeSensitiveTypes(List<Integer> sensitiveTypeIds) {
        boolean hasSensitiveWords = sensitiveWordService.existSensitiveWordByTypeId(sensitiveTypeIds);
        if(hasSensitiveWords){
            log.info("该敏感词分类列表存在不为空项，请先删除其中敏感词或将其合并到其他分类");
            return -1;
        }else{
            return sensitiveTypeMapper.deleteBatchIds(sensitiveTypeIds);
        }
    }

    @Override
    public int combineSensitiveInfo(int removeTypeId, int targetTypeId) {
        //***************************合并可能存在相同关键词的情况！***************************
        int wordsChange = sensitiveWordService.changeSensitiveWordsTypeIdTo(removeTypeId, targetTypeId);
        int typeRemove = this.removeSensitiveType(removeTypeId);
        int duplicateCount = sensitiveWordService.distinctSensitiveWords(targetTypeId);
        int duplicateRemoval = sensitiveWordService.duplicateRemoval(targetTypeId);
        log.info("合并去重了["+duplicateRemoval+"]条数据！");
        return duplicateCount;
    }
}
