package com.lyqf.qianfanmall.wx.service;

import com.lyqf.qianfanmall.db.domain.QianfanmallRegion;
import com.lyqf.qianfanmall.db.service.QianfanmallRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhy
 * @date 2019-01-17 23:07
 **/
@Component
public class GetRegionService {

	@Autowired
	private QianfanmallRegionService regionService;

	private static List<QianfanmallRegion> qianfanmallRegions;

	protected List<QianfanmallRegion> getQianfanmallRegions() {
		if(qianfanmallRegions==null){
			createRegion();
		}
		return qianfanmallRegions;
	}

	private synchronized void createRegion(){
		if (qianfanmallRegions == null) {
			qianfanmallRegions = regionService.getAll();
		}
	}
}
