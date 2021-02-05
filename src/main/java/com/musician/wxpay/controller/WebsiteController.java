package com.musician.wxpay.controller;


import com.musician.wxpay.dto.OrgWebsiteInfo;
import com.musician.wxpay.entity.WebsiteInfo;
import com.musician.wxpay.entity.WebsiteType;
import com.musician.wxpay.service.WebsiteInfoService;
import com.musician.wxpay.service.WebsiteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: LXR
 * @since: 2021/1/28 14:55
 */
@CrossOrigin
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WebsiteController {

    @Autowired
    private WebsiteTypeService websiteTypeService;

    @Autowired
    private WebsiteInfoService websiteInfoService;


    @PostMapping("/api/v1/getOrgWebsiteInfo")
    @ResponseBody
    public List<OrgWebsiteInfo> getOrgWebsiteInfo(){
        int orgId = 1;
        return websiteTypeService.packageAllWebsiteInfosByOrgId(orgId);
    }

    @PostMapping("/api/v1/addOrgWebsiteType")
    @ResponseBody
    public int addOrgWebsiteType(@RequestBody @Validated WebsiteType websiteType){
        return websiteTypeService.newWebsiteType(websiteType);
    }

    @GetMapping("/api/v1/getOrgWebsiteType")
    @ResponseBody
    public List<WebsiteType> getOrgWebsiteType(){
        int orgId = 1;
        return websiteTypeService.getWebsiteTypeByOrgId(orgId);
    }

    @PostMapping("/api/v1/addWebsiteInfo")
    @ResponseBody
    public int addWebsiteInfo(@RequestBody @Validated WebsiteInfo websiteInfo) {
        return websiteInfoService.newWebsiteInfo(websiteInfo);
    }

    @GetMapping("/api/v1/getWebsiteInfoByTypeId")
    @ResponseBody
    public List<WebsiteInfo> getWebsiteInfoByTypeId(@RequestParam @Validated int typeId) {
        return websiteInfoService.packageWebsiteInfosByType(typeId);
    }

    @GetMapping("/api/v1/searchWebsiteInfoByKeyWord")
    @ResponseBody
    public List<OrgWebsiteInfo> searchWebsiteInfoByKeyWord(@Validated String keyword){
        int orgId = 1;
        return websiteTypeService.packageAllWebsiteInfo(orgId, keyword);
    }

    @PostMapping("/api/v1/renameWebsiteType")
    @ResponseBody
    public int renameWebsiteType(@RequestBody @Validated WebsiteType websiteType){
        return websiteTypeService.renameWebsiteType(websiteType);
    }

    @GetMapping("/api/v1/removeWebsiteType")
    @ResponseBody
    public int removeWebsiteType(@Validated int websiteTypeId){
        return websiteTypeService.removeWebsiteType(websiteTypeId);
    }

    @PostMapping("/api/v1/removeWebsiteTypes")
    @ResponseBody
    public int removeWebsiteTypes(@RequestBody @Validated List<Integer> websiteTypeIds){
        return websiteTypeService.removeWebsiteTypes(websiteTypeIds);
    }

    @GetMapping("/api/v1/removeWebsiteInfo")
    @ResponseBody
    public int removeWebsiteInfo(@Validated int websiteInfoId){
        return websiteInfoService.removeWebsiteInfo(websiteInfoId);
    }

    @PostMapping("/api/v1/removeWebsiteInfos")
    @ResponseBody
    public int removeWebsiteInfos(@RequestBody @Validated List<Integer> websiteIds){
        return websiteInfoService.removeWebsiteInfos(websiteIds);
    }

    @GetMapping("/api/v1/combineWebsiteInfo")
    @ResponseBody
    public int combineWebsiteInfo(@RequestParam @Validated int removeTypeId,
                                     @RequestParam @Validated int targetTypeId){
        return websiteTypeService.combineWebsiteInfo(removeTypeId,targetTypeId);
    }

}
