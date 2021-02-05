package com.musician.wxpay.dto;



import com.musician.wxpay.entity.WebsiteInfo;
import com.musician.wxpay.entity.WebsiteType;
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
public class OrgWebsiteInfo {
    private WebsiteType websiteType;
    private List<WebsiteInfo> websiteInfos;
}
