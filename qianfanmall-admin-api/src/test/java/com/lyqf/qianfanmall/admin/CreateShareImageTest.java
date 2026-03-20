package com.lyqf.qianfanmall.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.lyqf.qianfanmall.core.qcode.QCodeService;
import com.lyqf.qianfanmall.db.domain.LitemallGoods;
import com.lyqf.qianfanmall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CreateShareImageTest {
    @Autowired
    QCodeService qCodeService;
    @Autowired
    LitemallGoodsService qianfanmallGoodsService;

    @Test
    public void test() {
        LitemallGoods good = qianfanmallGoodsService.findById(1181010);
        qCodeService.createGoodShareImage(good.getId().toString(), good.getPicUrl(), good.getName());
    }
}
