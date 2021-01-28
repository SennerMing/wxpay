package com.musician.wxpay;

import com.musician.wxpay.dao.SensitiveTypeMapper;
import com.musician.wxpay.entity.SensitiveType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
class WxpayApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private SensitiveTypeMapper sensitiveTypeMapper;

    @Test
    public void testSelect() {
        System.out.println("----- selectAll method test ------");
        List<SensitiveType> sensitiveTypeList = sensitiveTypeMapper.selectList(null);
        System.out.println(sensitiveTypeList.size());
    }

}
