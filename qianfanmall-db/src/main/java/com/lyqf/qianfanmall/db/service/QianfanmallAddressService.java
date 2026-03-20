package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallAddressMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallAddress;
import com.lyqf.qianfanmall.db.domain.QianfanmallAddressExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallAddressService {
    @Resource
    private QianfanmallAddressMapper addressMapper;

    public List<QianfanmallAddress> queryByUid(Integer uid) {
        QianfanmallAddressExample example = new QianfanmallAddressExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return addressMapper.selectByExample(example);
    }

    public QianfanmallAddress query(Integer userId, Integer id) {
        QianfanmallAddressExample example = new QianfanmallAddressExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public int add(QianfanmallAddress address) {
        address.setAddTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.insertSelective(address);
    }

    public int update(QianfanmallAddress address) {
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    public void delete(Integer id) {
        addressMapper.logicalDeleteByPrimaryKey(id);
    }

    public QianfanmallAddress findDefault(Integer userId) {
        QianfanmallAddressExample example = new QianfanmallAddressExample();
        example.or().andUserIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public void resetDefault(Integer userId) {
        QianfanmallAddress address = new QianfanmallAddress();
        address.setIsDefault(false);
        address.setUpdateTime(LocalDateTime.now());
        QianfanmallAddressExample example = new QianfanmallAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        addressMapper.updateByExampleSelective(address, example);
    }

    public List<QianfanmallAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        QianfanmallAddressExample example = new QianfanmallAddressExample();
        QianfanmallAddressExample.Criteria criteria = example.createCriteria();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return addressMapper.selectByExample(example);
    }
}
