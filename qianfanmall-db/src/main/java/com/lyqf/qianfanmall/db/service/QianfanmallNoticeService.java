package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallNoticeMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallNotice;
import com.lyqf.qianfanmall.db.domain.QianfanmallNoticeAdmin;
import com.lyqf.qianfanmall.db.domain.QianfanmallNoticeAdminExample;
import com.lyqf.qianfanmall.db.domain.QianfanmallNoticeExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallNoticeService {
    @Resource
    private QianfanmallNoticeMapper noticeMapper;


    public List<QianfanmallNotice> querySelective(String title, String content, Integer page, Integer limit, String sort, String order) {
        QianfanmallNoticeExample example = new QianfanmallNoticeExample();
        QianfanmallNoticeExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtils.isEmpty(content)) {
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return noticeMapper.selectByExample(example);
    }

    public int updateById(QianfanmallNotice notice) {
        notice.setUpdateTime(LocalDateTime.now());
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    public void deleteById(Integer id) {
        noticeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(QianfanmallNotice notice) {
        notice.setAddTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        noticeMapper.insertSelective(notice);
    }

    public QianfanmallNotice findById(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    public void deleteByIds(List<Integer> ids) {
        QianfanmallNoticeExample example = new QianfanmallNoticeExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        QianfanmallNotice notice = new QianfanmallNotice();
        notice.setUpdateTime(LocalDateTime.now());
        notice.setDeleted(true);
        noticeMapper.updateByExampleSelective(notice, example);
    }
}
