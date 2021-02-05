package com.musician.wxpay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.musician.wxpay.dao.SensitiveWordMapper;
import com.musician.wxpay.entity.SensitiveType;
import com.musician.wxpay.entity.SensitiveWord;
import com.musician.wxpay.service.SensitiveWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: LXR
 * @since: 2021/1/29 10:47
 */
@Service
@Slf4j
public class SensitiveWordServiceImpl implements SensitiveWordService {

    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;

    @Override
    public List<SensitiveWord> packageSensitiveWordsByType(int typeId) {
        LambdaQueryWrapper<SensitiveWord> queryWrapper = new QueryWrapper<SensitiveWord>().lambda()
                .eq(SensitiveWord::getTypeId, typeId);
        List<SensitiveWord> sensitiveWordList = sensitiveWordMapper.selectList(queryWrapper);
        return sensitiveWordList;
    }

    @Override
    public List<SensitiveWord> packageAllSensitiveWords(String keyword) {
        LambdaQueryWrapper<SensitiveWord> queryWrapper = new QueryWrapper<SensitiveWord>().lambda()
                .like(SensitiveWord::getSensitiveWord, keyword).groupBy(SensitiveWord::getTypeId);
        List<SensitiveWord> sensitiveWordList = sensitiveWordMapper.selectList(queryWrapper);
        return sensitiveWordList;
    }

    @Override
    public int newSensitiveWord(SensitiveWord sensitiveWord) {
        LambdaQueryWrapper<SensitiveWord> queryWrapper = new QueryWrapper<SensitiveWord>().lambda()
                .eq(SensitiveWord::getTypeId, sensitiveWord.getTypeId())
                .eq(SensitiveWord::getSensitiveWord, sensitiveWord.getSensitiveWord());
        int count = sensitiveWordMapper.selectCount(queryWrapper);
        if(count > 0){
            log.info("已存在");
            return -1;
        }else{
            sensitiveWordMapper.insert(sensitiveWord);
        }
        return sensitiveWord.getId();
    }

    @Override
    public boolean existSensitiveWordByTypeId(List<Integer> sensitiveTypeIds) {
        LambdaQueryWrapper<SensitiveWord> queryWrapper = new QueryWrapper<SensitiveWord>().lambda()
                .in(SensitiveWord::getTypeId, sensitiveTypeIds);
        return sensitiveWordMapper.selectCount(queryWrapper) > 0 ? true : false;
    }

    @Override
    public int removeSensitiveWord(int sensitiveWordId) {
        return sensitiveWordMapper.deleteById(sensitiveWordId);
    }

    @Override
    public int removeSensitiveWords(List<Integer> sensitiveTypeIds) {
        return sensitiveWordMapper.deleteBatchIds(sensitiveTypeIds);
    }

    @Override
    public int changeSensitiveWordsTypeIdTo(int originTypeId,int newSensitiveTypeId) {
        return sensitiveWordMapper.update(null, Wrappers.<SensitiveWord>lambdaUpdate()
                .set(SensitiveWord::getTypeId, newSensitiveTypeId).eq(SensitiveWord::getTypeId, originTypeId));
    }

    @Override
    public int distinctSensitiveWords(int typeId) {
        QueryWrapper<SensitiveWord> queryWrapper = new QueryWrapper<SensitiveWord>();
        queryWrapper.select("sensitive_word,count(sensitive_word) as count").groupBy("sensitive_word");
        List<SensitiveWord> sensitiveWordList = sensitiveWordMapper.selectList(queryWrapper);
        int count = 0;
        for(int i = 0;i < sensitiveWordList.size();i++){
            if(sensitiveWordList.get(i).getCount()>1){
                count++;
            }
        }
        return count;
    }

    @Override
    public int duplicateRemoval(int typeId) {
        return sensitiveWordMapper.duplicateRemoval(typeId);
    }
}
