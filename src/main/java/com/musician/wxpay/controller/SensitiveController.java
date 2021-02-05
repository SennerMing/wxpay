package com.musician.wxpay.controller;

import com.musician.wxpay.dto.OrgSensitiveInfo;
import com.musician.wxpay.entity.SensitiveType;
import com.musician.wxpay.entity.SensitiveWord;
import com.musician.wxpay.service.SensitiveTypeService;
import com.musician.wxpay.service.SensitiveWordService;
import com.musician.wxpay.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: LXR
 * @since: 2021/1/28 14:55
 */
@CrossOrigin
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SensitiveController {

    @Autowired
    private SensitiveTypeService sensitiveTypeService;

    @Autowired
    private SensitiveWordService sensitiveWordService;


    @PostMapping("/api/v1/getOrgSensitiveInfo")
    @ResponseBody
    public List<OrgSensitiveInfo> getOrgSensitiveInfo(){
        int orgId = 1;
        return sensitiveTypeService.packageAllSensitiveWordsByOrgId(orgId);
    }

    @PostMapping("/api/v1/addOrgSensitiveType")
    @ResponseBody
    public int addOrgSensitiveType(@RequestBody @Validated SensitiveType sensitiveType){
        return sensitiveTypeService.newSensitiveType(sensitiveType);
    }

    @GetMapping("/api/v1/getOrgSensitiveType")
    @ResponseBody
    public List<SensitiveType> getOrgSensitiveType(){
        int orgId = 1;
        return sensitiveTypeService.getSensitiveTypeByOrgId(orgId);
    }

    @PostMapping("/api/v1/addSensitiveWord")
    @ResponseBody
    public int addOrgSensitiveType(@RequestBody @Validated SensitiveWord sensitiveWord) {
        return sensitiveWordService.newSensitiveWord(sensitiveWord);
    }

    @GetMapping("/api/v1/getSensitiveWordByTypeId")
    @ResponseBody
    public List<SensitiveWord> getSensitiveWordByTypeId(@RequestParam @Validated int typeId) {
        return sensitiveWordService.packageSensitiveWordsByType(typeId);
    }

    @GetMapping("/api/v1/searchSensitiveInfoByKeyWord")
    @ResponseBody
    public List<OrgSensitiveInfo> searchSensitiveInfoByKeyWord(@Validated String keyword){
        int orgId = 1;
        return sensitiveTypeService.packageAllSensitiveInfo(orgId, keyword);
    }

    @PostMapping("/api/v1/renameSensitiveType")
    @ResponseBody
    public int renameSensitiveType(@RequestBody @Validated SensitiveType sensitiveType){
        return sensitiveTypeService.renameSensitiveType(sensitiveType);
    }

    @GetMapping("/api/v1/removeSensitiveType")
    @ResponseBody
    public int removeSensitiveType(@Validated int sensitiveTypeId){
        return sensitiveTypeService.removeSensitiveType(sensitiveTypeId);
    }

    @PostMapping("/api/v1/removeSensitiveTypes")
    @ResponseBody
    public int removeSensitiveTypes(@RequestBody @Validated List<Integer> sensitiveTypeIds){
        return sensitiveTypeService.removeSensitiveTypes(sensitiveTypeIds);
    }

    @GetMapping("/api/v1/removeSensitiveWord")
    @ResponseBody
    public int removeSensitiveWord(@Validated int sensitiveWordId){
        return sensitiveWordService.removeSensitiveWord(sensitiveWordId);
    }

    @PostMapping("/api/v1/removeSensitiveWords")
    @ResponseBody
    public int removeSensitiveWords(@RequestBody @Validated List<Integer> sensitiveWordIds){
        return sensitiveWordService.removeSensitiveWords(sensitiveWordIds);
    }

    @GetMapping("/api/v1/combineSensitiveInfo")
    @ResponseBody
    public int combineSensitiveInfo(@RequestParam @Validated int removeTypeId,
                                     @RequestParam @Validated int targetTypeId){
        return sensitiveTypeService.combineSensitiveInfo(removeTypeId,targetTypeId);
    }


    @PostMapping("/api/v1/getVerifyCode")
    @ResponseBody
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        VerifyCode verifyCode = new VerifyCode();

        InputStream is= null;
        try {
            BufferedImage bi = verifyCode.getImage();
            String verifyCodeText = verifyCode.getText();
            HttpSession session = request.getSession();
            System.out.println(verifyCodeText);
            session.setAttribute("vcode", verifyCodeText);

            ImageIO.write(bi, "JPEG", response.getOutputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/api/v1/login")
    @ResponseBody
    public boolean login(HttpServletRequest request, HttpServletResponse response,@RequestParam String verifycode){
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("vcode");
        System.out.println("session获取："+code);

        System.out.println("用户输入的verifycode："+verifycode);
        if(verifycode.equals(code)){
            return true;
        }else{
            return false;
        }



    }



}
