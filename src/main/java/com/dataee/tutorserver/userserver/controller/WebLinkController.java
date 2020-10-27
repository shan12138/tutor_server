package com.dataee.tutorserver.userserver.controller;

import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.userserver.bean.WebLinkResponseBean;
import com.dataee.tutorserver.userserver.service.IWebLinkService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author JinYue
 * @CreateDate 2019/6/18 23:53
 */
@RestController
public class WebLinkController {
    private Logger logger = LoggerFactory.getLogger(WebLinkController.class);

    @Autowired
    private IWebLinkService webLinkService;

    @ApiOperation("获取连接地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class)
    })
    @RequestMapping(value = "/link", method = RequestMethod.GET)
    public ResponseEntity getWebLink(@RequestParam String keyword) throws IllegalParameterException {
        if (keyword == null || keyword.equals("")) {
            throw new IllegalParameterException();
        }
        String address = webLinkService.getWebLink(keyword);
        if (address == null || address.equals("")) {
            throw new IllegalParameterException();
        } else {
            return ResultUtil.success(new WebLinkResponseBean(address));
        }
    }
}
