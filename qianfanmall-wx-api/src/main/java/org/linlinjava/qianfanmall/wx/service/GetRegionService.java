package org.linlinjava.qianfanmall.wx.service;

import org.linlinjava.qianfanmall.db.domain.LitemallRegion;
import org.linlinjava.qianfanmall.db.service.LitemallRegionService;
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
	private LitemallRegionService regionService;

	private static List<LitemallRegion> qianfanmallRegions;

	protected List<LitemallRegion> getLitemallRegions() {
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
