package com.musician.wxpay.service;


import java.util.List;

public interface SensitiveTypeService {

    List<OrgSensitiveInfo> packageAllSensitiveWordsByOrgId(int orgId);

    OrgSensitiveInfo packageAllSensitiveWordsByTypeId(int typeId);

    List<OrgSensitiveInfo> packageAllSensitiveInfo(int orgId, String keyword);

    int newSensitiveType(SensitiveType sensitiveType);

    List<SensitiveType> getSensitiveTypeByOrgId(int orgId);

    int renameSensitiveType(SensitiveType sensitiveType);

    int removeSensitiveType(int id);

    int removeSensitiveTypes(List<Integer> sensitiveTypeIds);

    int combineSensitiveInfo(int removeTypeId, int targetTypeId);

}
