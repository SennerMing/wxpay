package com.musician.wxpay;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
