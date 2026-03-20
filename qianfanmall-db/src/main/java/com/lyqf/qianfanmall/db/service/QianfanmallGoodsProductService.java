package com.lyqf.qianfanmall.db.service;

import org.apache.ibatis.annotations.Param;
import com.lyqf.qianfanmall.db.dao.GoodsProductMapper;
import com.lyqf.qianfanmall.db.dao.QianfanmallGoodsProductMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallGoodsProduct;
import com.lyqf.qianfanmall.db.domain.QianfanmallGoodsProductExample;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallGoodsProductService {
    @Resource
    private QianfanmallGoodsProductMapper qianfanmallGoodsProductMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;

    public List<QianfanmallGoodsProduct> queryByGid(Integer gid) {
        QianfanmallGoodsProductExample example = new QianfanmallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return qianfanmallGoodsProductMapper.selectByExample(example);
    }

    public QianfanmallGoodsProduct findById(Integer id) {
        return qianfanmallGoodsProductMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        qianfanmallGoodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(QianfanmallGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        qianfanmallGoodsProductMapper.insertSelective(goodsProduct);
    }

    public int count() {
        QianfanmallGoodsProductExample example = new QianfanmallGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) qianfanmallGoodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        QianfanmallGoodsProductExample example = new QianfanmallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        qianfanmallGoodsProductMapper.logicalDeleteByExample(example);
    }

    public int addStock(Integer id, Short num){
        return goodsProductMapper.addStock(id, num);
    }

    public int reduceStock(Integer id, Short num){
        return goodsProductMapper.reduceStock(id, num);
    }

    public void updateById(QianfanmallGoodsProduct product) {
        product.setUpdateTime(LocalDateTime.now());
        qianfanmallGoodsProductMapper.updateByPrimaryKeySelective(product);
    }
}