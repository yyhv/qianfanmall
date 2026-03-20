package com.lyqf.qianfanmall.db.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallRoleMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallRole;
import com.lyqf.qianfanmall.db.domain.QianfanmallRoleExample;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QianfanmallRoleService {
    @Resource
    private QianfanmallRoleMapper roleMapper;


    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<String>();
        if(roleIds.length == 0){
            return roles;
        }

        QianfanmallRoleExample example = new QianfanmallRoleExample();
        example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
        List<QianfanmallRole> roleList = roleMapper.selectByExample(example);

        for(QianfanmallRole role : roleList){
            roles.add(role.getName());
        }

        return roles;

    }

    public List<QianfanmallRole> querySelective(String name, Integer page, Integer limit, String sort, String order) {
        QianfanmallRoleExample example = new QianfanmallRoleExample();
        QianfanmallRoleExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return roleMapper.selectByExample(example);
    }

    public QianfanmallRole findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public void add(QianfanmallRole role) {
        role.setAddTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insertSelective(role);
    }

    public void deleteById(Integer id) {
        roleMapper.logicalDeleteByPrimaryKey(id);
    }

    public void updateById(QianfanmallRole role) {
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    public boolean checkExist(String name) {
        QianfanmallRoleExample example = new QianfanmallRoleExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return roleMapper.countByExample(example) != 0;
    }

    public List<QianfanmallRole> queryAll() {
        QianfanmallRoleExample example = new QianfanmallRoleExample();
        example.or().andDeletedEqualTo(false);
        return roleMapper.selectByExample(example);
    }
}
