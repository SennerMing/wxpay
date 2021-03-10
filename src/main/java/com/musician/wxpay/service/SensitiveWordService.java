package com.musician.wxpay.service;

import java.util.List;

/**
 * @author: LXR
 * @since: 2021/1/29 10:46
 */
public interface SensitiveWordService {

    List<SensitiveWord> packageSensitiveWordsByType(int typeId);

    List<SensitiveWord> packageAllSensitiveWords(String keyword);

    int newSensitiveWord(SensitiveWord sensitiveWord);

    int removeSensitiveWord(int sensitiveWordId);

    int removeSensitiveWords(List<Integer> sensitiveTypeIds);

    boolean existSensitiveWordByTypeId(List<Integer> sensitiveTypeIds);

    int changeSensitiveWordsTypeIdTo(int originTypeId, int newSensitiveTypeId);

    int distinctSensitiveWords(int typeId);

    int duplicateRemoval(int typeId);
}
