package com.musician.wxpay.service;


import com.musician.wxpay.dto.OrgSensitiveInfo;
import com.musician.wxpay.entity.SensitiveType;

import java.util.List;

public interface SensitiveTypeService {

    List<OrgSensitiveInfo> packageAllSensitiveWordsByOrgId(int orgId);

    OrgSensitiveInfo packageAllSensitiveWordsByTypeId(int typeId);

    List<OrgSensitiveInfo> packageAllSensitiveInfo(int orgId, String keyword);

    int newSensitiveType(SensitiveType sensitiveType);

    int renameSensitiveType(SensitiveType sensitiveType);

    int removeSensitiveType(int id);

    int removeSensitiveTypes(List<Integer> sensitiveTypeIds);

    int combineSensitiveInfo(int removeTypeId, int targetTypeId);

}
