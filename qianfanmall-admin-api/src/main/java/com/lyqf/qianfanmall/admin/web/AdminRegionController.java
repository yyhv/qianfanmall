package com.lyqf.qianfanmall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lyqf.qianfanmall.admin.vo.RegionVo;
import com.lyqf.qianfanmall.core.util.ResponseUtil;
import com.lyqf.qianfanmall.db.domain.QianfanmallRegion;
import com.lyqf.qianfanmall.db.service.QianfanmallRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/region")
@Validated
public class AdminRegionController {
    private final Log logger = LogFactory.getLog(AdminRegionController.class);

    @Autowired
    private QianfanmallRegionService regionService;

    @GetMapping("/clist")
    public Object clist(@NotNull Integer id) {
        List<QianfanmallRegion> regionList = regionService.queryByPid(id);
        return ResponseUtil.okList(regionList);
    }

    @GetMapping("/list")
    public Object list() {
        List<RegionVo> regionVoList = new ArrayList<>();

        List<QianfanmallRegion> qianfanmallRegions = regionService.getAll();
        Map<Byte, List<QianfanmallRegion>> collect = qianfanmallRegions.stream().collect(Collectors.groupingBy(QianfanmallRegion::getType));
        byte provinceType = 1;
        List<QianfanmallRegion> provinceList = collect.get(provinceType);
        byte cityType = 2;
        List<QianfanmallRegion> city = collect.get(cityType);
        Map<Integer, List<QianfanmallRegion>> cityListMap = city.stream().collect(Collectors.groupingBy(QianfanmallRegion::getPid));
        byte areaType = 3;
        List<QianfanmallRegion> areas = collect.get(areaType);
        Map<Integer, List<QianfanmallRegion>> areaListMap = areas.stream().collect(Collectors.groupingBy(QianfanmallRegion::getPid));

        for (QianfanmallRegion province : provinceList) {
            RegionVo provinceVO = new RegionVo();
            provinceVO.setId(province.getId());
            provinceVO.setName(province.getName());
            provinceVO.setCode(province.getCode());
            provinceVO.setType(province.getType());

            List<QianfanmallRegion> cityList = cityListMap.get(province.getId());
            List<RegionVo> cityVOList = new ArrayList<>();
            for (QianfanmallRegion cityVo : cityList) {
                RegionVo cityVO = new RegionVo();
                cityVO.setId(cityVo.getId());
                cityVO.setName(cityVo.getName());
                cityVO.setCode(cityVo.getCode());
                cityVO.setType(cityVo.getType());

                List<QianfanmallRegion> areaList = areaListMap.get(cityVo.getId());
                List<RegionVo> areaVOList = new ArrayList<>();
                for (QianfanmallRegion area : areaList) {
                    RegionVo areaVO = new RegionVo();
                    areaVO.setId(area.getId());
                    areaVO.setName(area.getName());
                    areaVO.setCode(area.getCode());
                    areaVO.setType(area.getType());
                    areaVOList.add(areaVO);
                }

                cityVO.setChildren(areaVOList);
                cityVOList.add(cityVO);
            }
            provinceVO.setChildren(cityVOList);
            regionVoList.add(provinceVO);
        }

        return ResponseUtil.okList(regionVoList);
    }
}
