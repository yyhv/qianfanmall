package com.lyqf.qianfanmall.db.service;

import com.github.pagehelper.PageHelper;
import com.lyqf.qianfanmall.db.dao.QianfanmallTopicMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallGroupon;
import com.lyqf.qianfanmall.db.domain.QianfanmallTopic;
import com.lyqf.qianfanmall.db.domain.QianfanmallTopic.Column;
import com.lyqf.qianfanmall.db.domain.QianfanmallTopicExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QianfanmallTopicService {
    @Resource
    private QianfanmallTopicMapper topicMapper;
    private Column[] columns = new Column[]{Column.id, Column.title, Column.subtitle, Column.price, Column.picUrl, Column.readCount};

    public List<QianfanmallTopic> queryList(int offset, int limit) {
        return queryList(offset, limit, "add_time", "desc");
    }

    public List<QianfanmallTopic> queryList(int offset, int limit, String sort, String order) {
        QianfanmallTopicExample example = new QianfanmallTopicExample();
        example.or().andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(offset, limit);
        return topicMapper.selectByExampleSelective(example, columns);
    }

    public int queryTotal() {
        QianfanmallTopicExample example = new QianfanmallTopicExample();
        example.or().andDeletedEqualTo(false);
        return (int) topicMapper.countByExample(example);
    }

    public QianfanmallTopic findById(Integer id) {
        QianfanmallTopicExample example = new QianfanmallTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return topicMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<QianfanmallTopic> queryRelatedList(Integer id, int offset, int limit) {
        QianfanmallTopicExample example = new QianfanmallTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<QianfanmallTopic> topics = topicMapper.selectByExample(example);
        if (topics.size() == 0) {
            return queryList(offset, limit, "add_time", "desc");
        }
        QianfanmallTopic topic = topics.get(0);

        example = new QianfanmallTopicExample();
        example.or().andIdNotEqualTo(topic.getId()).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        List<QianfanmallTopic> relateds = topicMapper.selectByExampleWithBLOBs(example);
        if (relateds.size() != 0) {
            return relateds;
        }

        return queryList(offset, limit, "add_time", "desc");
    }

    public List<QianfanmallTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort, String order) {
        QianfanmallTopicExample example = new QianfanmallTopicExample();
        QianfanmallTopicExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtils.isEmpty(subtitle)) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int updateById(QianfanmallTopic topic) {
        topic.setUpdateTime(LocalDateTime.now());
        QianfanmallTopicExample example = new QianfanmallTopicExample();
        example.or().andIdEqualTo(topic.getId());
        return topicMapper.updateByExampleSelective(topic, example);
    }

    public void deleteById(Integer id) {
        topicMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(QianfanmallTopic topic) {
        topic.setAddTime(LocalDateTime.now());
        topic.setUpdateTime(LocalDateTime.now());
        topicMapper.insertSelective(topic);
    }


    public void deleteByIds(List<Integer> ids) {
        QianfanmallTopicExample example = new QianfanmallTopicExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        QianfanmallTopic topic = new QianfanmallTopic();
        topic.setUpdateTime(LocalDateTime.now());
        topic.setDeleted(true);
        topicMapper.updateByExampleSelective(topic, example);
    }
}
