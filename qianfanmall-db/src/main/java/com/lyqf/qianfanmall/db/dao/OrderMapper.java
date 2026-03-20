package com.lyqf.qianfanmall.db.dao;

import org.apache.ibatis.annotations.Param;
import com.lyqf.qianfanmall.db.domain.QianfanmallOrder;
import com.lyqf.qianfanmall.db.domain.OrderVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") QianfanmallOrder order);
    List<Map> getOrderIds(@Param("query") String query, @Param("orderByClause") String orderByClause);
    List<OrderVo> getOrderList(@Param("query") String query, @Param("orderByClause") String orderByClause);
}