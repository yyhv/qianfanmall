package com.lyqf.qianfanmall.db;

import org.junit.Test;
import com.lyqf.qianfanmall.db.util.DbUtil;

import java.io.File;

public class DbUtilTest {
    @Test
    public void testBackup() {
        File file = new File("test.sql");
        DbUtil.backup(file, "qianfanmall", "qianfanmall123456", "qianfanmall");
    }

//    这个测试用例会重置qianfanmall数据库，所以比较危险，请开发者注意
//    @Test
    public void testLoad() {
        File file = new File("test.sql");
        DbUtil.load(file, "qianfanmall", "qianfanmall123456", "qianfanmall");
    }
}
