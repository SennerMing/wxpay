package com.musician.wxpay.service;

import java.util.List;

/**
 * @author: LXR
 * @since: 2021/1/29 10:46
 */
public interface WebsiteInfoService {

    List<WebsiteInfo> packageWebsiteInfosByType(int typeId);

    List<WebsiteInfo> packageAllWebsiteInfos(String siteName);

    int newWebsiteInfo(WebsiteInfo websiteInfo);

    int removeWebsiteInfo(int websiteId);

    int removeWebsiteInfos(List<Integer> websiteTypeIds);

    boolean existWebsiteInfoTypeId(List<Integer> websiteTypeIds);

    int changeWebsiteInfoTypeIdTo(int originTypeId, int newTypeId);

    int distinctWebsiteInfo(int typeId);

    int duplicateRemoval(int typeId);
}
