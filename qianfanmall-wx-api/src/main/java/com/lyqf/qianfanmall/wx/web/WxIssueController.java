package com.lyqf.qianfanmall.wx.web;

import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lyqf.qianfanmall.core.util.ResponseUtil;
import com.lyqf.qianfanmall.core.validator.Order;
import com.lyqf.qianfanmall.core.validator.Sort;
import com.lyqf.qianfanmall.db.domain.LitemallIssue;
import com.lyqf.qianfanmall.db.service.LitemallIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/issue")
@Validated
public class WxIssueController {
    private final Log logger = LogFactory.getLog(WxIssueController.class);

    @Autowired
    private LitemallIssueService issueService;

    /**
     * 帮助中心
     */
    @GetMapping("/list")
    public Object list(String question,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallIssue> issueList = issueService.querySelective(question, page, size, sort, order);
        return ResponseUtil.okList(issueList);
    }

}
