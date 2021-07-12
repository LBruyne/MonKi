package com.hinsliu.monki.web.controller;

import com.hinsliu.monki.domain.common.RpcResult;
import com.hinsliu.monki.domain.query.RecommendQuery;
import com.hinsliu.monki.domain.query.SearchQuery;
import com.hinsliu.monki.domain.query.UserLoginQuery;
import com.hinsliu.monki.service.SearchEngineManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:02
 * @Description: 搜索引擎服务
 */
@Api(value = "搜索引擎接口")
@Slf4j
@RestController
@RequestMapping("/app/engine")
public class SearchEngineController {

    @Resource
    private SearchEngineManager searchEngineManager;

    @ApiOperation(value = "搜索服务，返回搜索的电影列表")
    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public RpcResult search(@ModelAttribute @Validated SearchQuery query) {
        return RpcResult.successResult(searchEngineManager.search());
    }

    @ApiOperation(value = "推荐服务，返回推荐的电影列表")
    @RequestMapping(value = "/recommend", method = {RequestMethod.GET})
    public RpcResult recommend(@ModelAttribute @Validated RecommendQuery query) {
        return RpcResult.successResult(searchEngineManager.recommend());
    }



}
