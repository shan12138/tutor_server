package com.dataee.tutorserver.userserver.controller;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.userserver.service.IPaperResourceService;
import com.dataee.tutorserver.utils.ResultUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 获取指定路径的资源链接
 *
 * @author JinYue
 * @CreateDate 2019/6/19 17:08
 */
@RestController
public class PaperResourceController {
    private Logger logger = LoggerFactory.getLogger(PaperResourceController.class);

    @Autowired
    private IPaperResourceService paperResourceService;

    /**
     * 获取某个试卷的某个资源图片
     *
     * @param address
     * @return
     */
    @ApiOperation("获取某个试卷的某个资源图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "地址", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class)
    })
    @RequestMapping(value = "/paper/resource", method = RequestMethod.GET)
    public ResponseEntity getPaperResource(@RequestParam String address) throws BaseServiceException {
        String urlStr = paperResourceService.getPaperImageLinkByAddress(address);
        return ResultUtil.success(urlStr);

    }

    /**
     * 获取试卷图片链接的list
     *
     * @param addresses
     * @return
     */
    @ApiOperation("获取试卷图片链接的list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addresses", value = "地址列表", required = true, dataType = "String", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class, responseContainer = "list")
    })
    @RequestMapping(value = "/paper/resource/list", method = RequestMethod.POST)
    public ResponseEntity getPaperResourceList(@RequestBody List<String> addresses) throws BaseServiceException {
        List<String> urlStrList = paperResourceService.getPaperImageLinkListByAddresses(addresses);
        return ResultUtil.success(urlStrList);

    }

}
