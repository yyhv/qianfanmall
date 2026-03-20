package com.lyqf.qianfanmall.db.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallGoodsMapper;
import com.lyqf.qianfanmall.db.dao.QianfanmallGrouponRulesMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallGoods;
import com.lyqf.qianfanmall.db.domain.QianfanmallGrouponRules;
import com.lyqf.qianfanmall.db.domain.QianfanmallGrouponRulesExample;
import com.lyqf.qianfanmall.db.util.GrouponConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QianfanmallGrouponRulesService {
    @Resource
    private QianfanmallGrouponRulesMapper mapper;
    @Resource
    private QianfanmallGoodsMapper goodsMapper;
    private QianfanmallGoods.Column[] goodsColumns = new QianfanmallGoods.Column[]{QianfanmallGoods.Column.id, QianfanmallGoods.Column.name, QianfanmallGoods.Column.brief, QianfanmallGoods.Column.picUrl, QianfanmallGoods.Column.counterPrice, QianfanmallGoods.Column.retailPrice};

    public int createRules(QianfanmallGrouponRules rules) {
        rules.setAddTime(LocalDateTime.now());
        rules.setUpdateTime(LocalDateTime.now());
        return mapper.insertSelective(rules);
    }

    /**
     * 根据ID查找对应团购项
     *
     * @param id
     * @return
     */
    public QianfanmallGrouponRules findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询某个商品关联的团购规则
     *
     * @param goodsId
     * @return
     */
    public List<QianfanmallGrouponRules> queryByGoodsId(Integer goodsId) {
        QianfanmallGrouponRulesExample example = new QianfanmallGrouponRulesExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        return mapper.selectByExample(example);
    }

    public int countByGoodsId(Integer goodsId) {
        QianfanmallGrouponRulesExample example = new QianfanmallGrouponRulesExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        return (int)mapper.countByExample(example);
    }

    public List<QianfanmallGrouponRules> queryByStatus(Short status) {
        QianfanmallGrouponRulesExample example = new QianfanmallGrouponRulesExample();
        example.or().andStatusEqualTo(status).andDeletedEqualTo(false);
        return mapper.selectByExample(example);
    }

    /**
     * 获取首页团购规则列表
     *
     * @param page
     * @param limit
     * @return
     */
    public List<QianfanmallGrouponRules> queryList(Integer page, Integer limit) {
        return queryList(page, limit, "add_time", "desc");
    }

    public List<QianfanmallGrouponRules> queryList(Integer page, Integer limit, String sort, String order) {
        QianfanmallGrouponRulesExample example = new QianfanmallGrouponRulesExample();
        example.or().andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        return mapper.selectByExample(example);
    }

    /**
     * 判断某个团购规则是否已经过期
     *
     * @return
     */
    public boolean isExpired(QianfanmallGrouponRules rules) {
        return (rules == null || rules.getExpireTime().isBefore(LocalDateTime.now()));
    }

    /**
     * 获取团购规则列表
     *
     * @param goodsId
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    public List<QianfanmallGrouponRules> querySelective(String goodsId, Integer page, Integer size, String sort, String order) {
        QianfanmallGrouponRulesExample example = new QianfanmallGrouponRulesExample();
        example.setOrderByClause(sort + " " + order);

        QianfanmallGrouponRulesExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(Integer.parseInt(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return mapper.selectByExample(example);
    }

    public void delete(Integer id) {
        mapper.logicalDeleteByPrimaryKey(id);
    }

    public int updateById(QianfanmallGrouponRules grouponRules) {
        grouponRules.setUpdateTime(LocalDateTime.now());
        return mapper.updateByPrimaryKeySelective(grouponRules);
    }
}