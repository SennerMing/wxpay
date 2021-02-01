package com.musician.wxpay.dto;

import com.musician.wxpay.entity.SensitiveType;
import com.musician.wxpay.entity.SensitiveWord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: LXR
 * @since: 2021/1/29 11:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgSensitiveInfo {
    private SensitiveType sensitiveType;
    private List<SensitiveWord> sensitiveWordList;
}
