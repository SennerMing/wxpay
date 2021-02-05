package com.musician.wxpay.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author: LXR
 * @since: 2021/2/2 14:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IrregularCheckParam {

    private int websiteTypeId;
    private String websiteTypeName;
    private int websiteId;
    private String websiteName;
    private int sensitiveTypeId;
    private String sensitiveTypeName;
    private int sensitiveWordId;
    private String sensitiveWord;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCheckDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endCheckDate;
    private int page;
    private int pageNum;
}
