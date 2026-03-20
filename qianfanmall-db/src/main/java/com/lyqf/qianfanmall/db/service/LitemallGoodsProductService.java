package com.lyqf.qianfanmall.db.service;

import org.apache.ibatis.annotations.Param;
import com.lyqf.qianfanmall.db.dao.GoodsProductMapper;
import com.lyqf.qianfanmall.db.dao.LitemallGoodsProductMapper;
import com.lyqf.qianfanmall.db.domain.LitemallGoodsProduct;
import com.lyqf.qianfanmall.db.domain.LitemallGoodsProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallGoodsProductService {
    @Resource
    private LitemallGoodsProductMapper qianfanmallGoodsProductMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;

    public List<LitemallGoodsProduct> queryByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return qianfanmallGoodsProductMapper.selectByExample(example);
    }

    public LitemallGoodsProduct findById(Integer id) {
        return qianfanmallGoodsProductMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        qianfanmallGoodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        qianfanmallGoodsProductMapper.insertSelective(goodsProduct);
    }

    public int count() {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) qianfanmallGoodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        qianfanmallGoodsProductMapper.logicalDeleteByExample(example);
    }

    public int addStock(Integer id, Short num){
        return goodsProductMapper.addStock(id, num);
    }

    public int reduceStock(Integer id, Short num){
        return goodsProductMapper.reduceStock(id, num);
    }

    public void updateById(LitemallGoodsProduct product) {
        product.setUpdateTime(LocalDateTime.now());
        qianfanmallGoodsProductMapper.updateByPrimaryKeySelective(product);
    }
}