package com.lyqf.qianfanmall.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.lyqf.qianfanmall.db.dao.QianfanmallSystemMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperReturnTest {

    @Autowired
    private QianfanmallSystemMapper systemMapper;

    @Test
    public void test() {
        QianfanmallSystem system = new QianfanmallSystem();
        system.setKeyName("test-system-key");
        system.setKeyValue("test-system-value");
        int updates = systemMapper.insertSelective(system);
        Assert.assertEquals(updates, 1);

        updates = systemMapper.deleteByPrimaryKey(system.getId());
        Assert.assertEquals(updates, 1);

        updates = systemMapper.updateByPrimaryKey(system);
        Assert.assertEquals(updates, 0);
    }

}

