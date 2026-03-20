package com.lyqf.qianfanmall.db.service;

import com.lyqf.qianfanmall.db.dao.QianfanmallGoodsAttributeMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallGoodsAttribute;
import com.lyqf.qianfanmall.db.domain.QianfanmallGoodsAttributeExample;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallGoodsAttributeService {
    @Resource
    private QianfanmallGoodsAttributeMapper goodsAttributeMapper;

    public List<QianfanmallGoodsAttribute> queryByGid(Integer goodsId) {
        QianfanmallGoodsAttributeExample example = new QianfanmallGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return goodsAttributeMapper.selectByExample(example);
    }

    public void add(QianfanmallGoodsAttribute goodsAttribute) {
        goodsAttribute.setAddTime(LocalDateTime.now());
        goodsAttribute.setUpdateTime(LocalDateTime.now());
        goodsAttributeMapper.insertSelective(goodsAttribute);
    }

    public QianfanmallGoodsAttribute findById(Integer id) {
        return goodsAttributeMapper.selectByPrimaryKey(id);
    }

    public void deleteByGid(Integer gid) {
        QianfanmallGoodsAttributeExample example = new QianfanmallGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsAttributeMapper.logicalDeleteByExample(example);
    }

    public void deleteById(Integer id) {
        goodsAttributeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void updateById(QianfanmallGoodsAttribute attribute) {
        attribute.setUpdateTime(LocalDateTime.now());
        goodsAttributeMapper.updateByPrimaryKeySelective(attribute);
    }
}
