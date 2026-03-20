package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallBrandMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallBrand;
import com.lyqf.qianfanmall.db.domain.QianfanmallBrand.Column;
import com.lyqf.qianfanmall.db.domain.QianfanmallBrandExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallBrandService {
    @Resource
    private QianfanmallBrandMapper brandMapper;
    private Column[] columns = new Column[]{Column.id, Column.name, Column.desc, Column.picUrl, Column.floorPrice};

    public List<QianfanmallBrand> query(Integer page, Integer limit, String sort, String order) {
        QianfanmallBrandExample example = new QianfanmallBrandExample();
        example.or().andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return brandMapper.selectByExampleSelective(example, columns);
    }

    public List<QianfanmallBrand> query(Integer page, Integer limit) {
        return query(page, limit, null, null);
    }

    public QianfanmallBrand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<QianfanmallBrand> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        QianfanmallBrandExample example = new QianfanmallBrandExample();
        QianfanmallBrandExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return brandMapper.selectByExample(example);
    }

    public int updateById(QianfanmallBrand brand) {
        brand.setUpdateTime(LocalDateTime.now());
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    public void deleteById(Integer id) {
        brandMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(QianfanmallBrand brand) {
        brand.setAddTime(LocalDateTime.now());
        brand.setUpdateTime(LocalDateTime.now());
        brandMapper.insertSelective(brand);
    }

    public List<QianfanmallBrand> all() {
        QianfanmallBrandExample example = new QianfanmallBrandExample();
        example.or().andDeletedEqualTo(false);
        return brandMapper.selectByExample(example);
    }
}
