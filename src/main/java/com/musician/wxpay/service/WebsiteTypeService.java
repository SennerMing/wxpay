package com.musician.wxpay.service;

import java.util.List;

public interface WebsiteTypeService {

    List<OrgWebsiteInfo> packageAllWebsiteInfosByOrgId(int orgId);

    OrgWebsiteInfo packageAllWebsitesInfoByType(int typeId);

    List<OrgWebsiteInfo> packageAllWebsiteInfo(int orgId, String keyword);

    int newWebsiteType(WebsiteType websiteType);

    List<WebsiteType> getWebsiteTypeByOrgId(int orgId);

    int renameWebsiteType(WebsiteType websiteType);

    int removeWebsiteType(int id);

    int removeWebsiteTypes(List<Integer> websiteTypeIds);

    int combineWebsiteInfo(int removeTypeId, int targetTypeId);

}
