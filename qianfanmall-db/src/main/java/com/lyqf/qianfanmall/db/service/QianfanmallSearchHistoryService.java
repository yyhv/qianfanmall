package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallSearchHistoryMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallSearchHistory;
import com.lyqf.qianfanmall.db.domain.QianfanmallSearchHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallSearchHistoryService {
    @Resource
    private QianfanmallSearchHistoryMapper searchHistoryMapper;

    public void save(QianfanmallSearchHistory searchHistory) {
        searchHistory.setAddTime(LocalDateTime.now());
        searchHistory.setUpdateTime(LocalDateTime.now());
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<QianfanmallSearchHistory> queryByUid(int uid) {
        QianfanmallSearchHistoryExample example = new QianfanmallSearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        example.setDistinct(true);
        return searchHistoryMapper.selectByExampleSelective(example, QianfanmallSearchHistory.Column.keyword);
    }

    public void deleteByUid(int uid) {
        QianfanmallSearchHistoryExample example = new QianfanmallSearchHistoryExample();
        example.or().andUserIdEqualTo(uid);
        searchHistoryMapper.logicalDeleteByExample(example);
    }

    public List<QianfanmallSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        QianfanmallSearchHistoryExample example = new QianfanmallSearchHistoryExample();
        QianfanmallSearchHistoryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return searchHistoryMapper.selectByExample(example);
    }
}
