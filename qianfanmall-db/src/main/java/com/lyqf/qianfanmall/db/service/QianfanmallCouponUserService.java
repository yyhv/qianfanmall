package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallCouponUserMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallCouponUser;
import com.lyqf.qianfanmall.db.domain.QianfanmallCouponUserExample;
import com.lyqf.qianfanmall.db.util.CouponUserConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallCouponUserService {
    @Resource
    private QianfanmallCouponUserMapper couponUserMapper;

    public Integer countCoupon(Integer couponId) {
        QianfanmallCouponUserExample example = new QianfanmallCouponUserExample();
        example.or().andCouponIdEqualTo(couponId).andDeletedEqualTo(false);
        return (int)couponUserMapper.countByExample(example);
    }

    public Integer countUserAndCoupon(Integer userId, Integer couponId) {
        QianfanmallCouponUserExample example = new QianfanmallCouponUserExample();
        example.or().andUserIdEqualTo(userId).andCouponIdEqualTo(couponId).andDeletedEqualTo(false);
        return (int)couponUserMapper.countByExample(example);
    }

    public void add(QianfanmallCouponUser couponUser) {
        couponUser.setAddTime(LocalDateTime.now());
        couponUser.setUpdateTime(LocalDateTime.now());
        couponUserMapper.insertSelective(couponUser);
    }

    public List<QianfanmallCouponUser> queryList(Integer userId, Integer couponId, Short status, Integer page, Integer size, String sort, String order) {
        QianfanmallCouponUserExample example = new QianfanmallCouponUserExample();
        QianfanmallCouponUserExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(couponId != null){
            criteria.andCouponIdEqualTo(couponId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(size)) {
            PageHelper.startPage(page, size);
        }

        return couponUserMapper.selectByExample(example);
    }

    public List<QianfanmallCouponUser> queryAll(Integer userId, Integer couponId) {
        return queryList(userId, couponId, CouponUserConstant.STATUS_USABLE, null, null, "add_time", "desc");
    }

    public List<QianfanmallCouponUser> queryAll(Integer userId) {
        return queryList(userId, null, CouponUserConstant.STATUS_USABLE, null, null, "add_time", "desc");
    }

    public QianfanmallCouponUser queryOne(Integer userId, Integer couponId) {
        List<QianfanmallCouponUser> couponUserList = queryList(userId, couponId, CouponUserConstant.STATUS_USABLE, 1, 1, "add_time", "desc");
        if(couponUserList.size() == 0){
            return null;
        }
        return couponUserList.get(0);
    }

    public QianfanmallCouponUser findById(Integer id) {
        return couponUserMapper.selectByPrimaryKey(id);
    }


    public int update(QianfanmallCouponUser couponUser) {
        couponUser.setUpdateTime(LocalDateTime.now());
        return couponUserMapper.updateByPrimaryKeySelective(couponUser);
    }

    public List<QianfanmallCouponUser> queryExpired() {
        QianfanmallCouponUserExample example = new QianfanmallCouponUserExample();
        example.or().andStatusEqualTo(CouponUserConstant.STATUS_USABLE).andEndTimeLessThan(LocalDateTime.now()).andDeletedEqualTo(false);
        return couponUserMapper.selectByExample(example);
    }

    public List<QianfanmallCouponUser> findByOid(Integer orderId) {
        QianfanmallCouponUserExample example = new QianfanmallCouponUserExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return couponUserMapper.selectByExample(example);
    }
}
