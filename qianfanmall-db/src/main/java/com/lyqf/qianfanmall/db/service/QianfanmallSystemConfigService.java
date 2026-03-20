package com.lyqf.qianfanmall.db.service;

import com.lyqf.qianfanmall.db.dao.QianfanmallSystemMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallSystem;
import com.lyqf.qianfanmall.db.domain.QianfanmallSystemExample;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QianfanmallSystemConfigService {
    @Resource
    private QianfanmallSystemMapper systemMapper;

    public Map<String, String> queryAll() {
        QianfanmallSystemExample example = new QianfanmallSystemExample();
        example.or().andDeletedEqualTo(false);

        List<QianfanmallSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> systemConfigs = new HashMap<>();
        for (QianfanmallSystem item : systemList) {
            systemConfigs.put(item.getKeyName(), item.getKeyValue());
        }

        return systemConfigs;
    }

    public Map<String, String> listMail() {
        QianfanmallSystemExample example = new QianfanmallSystemExample();
        example.or().andKeyNameLike("qianfanmall_mall_%").andDeletedEqualTo(false);
        List<QianfanmallSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(QianfanmallSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listWx() {
        QianfanmallSystemExample example = new QianfanmallSystemExample();
        example.or().andKeyNameLike("qianfanmall_wx_%").andDeletedEqualTo(false);
        List<QianfanmallSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(QianfanmallSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listOrder() {
        QianfanmallSystemExample example = new QianfanmallSystemExample();
        example.or().andKeyNameLike("qianfanmall_order_%").andDeletedEqualTo(false);
        List<QianfanmallSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(QianfanmallSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listExpress() {
        QianfanmallSystemExample example = new QianfanmallSystemExample();
        example.or().andKeyNameLike("qianfanmall_express_%").andDeletedEqualTo(false);
        List<QianfanmallSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(QianfanmallSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public void updateConfig(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            QianfanmallSystemExample example = new QianfanmallSystemExample();
            example.or().andKeyNameEqualTo(entry.getKey()).andDeletedEqualTo(false);

            QianfanmallSystem system = new QianfanmallSystem();
            system.setKeyName(entry.getKey());
            system.setKeyValue(entry.getValue());
            system.setUpdateTime(LocalDateTime.now());
            systemMapper.updateByExampleSelective(system, example);
        }

    }

    public void addConfig(String key, String value) {
        QianfanmallSystem system = new QianfanmallSystem();
        system.setKeyName(key);
        system.setKeyValue(value);
        system.setAddTime(LocalDateTime.now());
        system.setUpdateTime(LocalDateTime.now());
        systemMapper.insertSelective(system);
    }
}
