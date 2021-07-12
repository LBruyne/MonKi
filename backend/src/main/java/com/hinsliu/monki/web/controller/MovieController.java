package com.hinsliu.monki.web.controller;

import com.hinsliu.monki.common.annotation.AuthToken;
import com.hinsliu.monki.domain.common.RpcResult;
import com.hinsliu.monki.domain.query.MovieInfoQuery;
import com.hinsliu.monki.domain.query.RecommendQuery;
import com.hinsliu.monki.service.MovieManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:54
 * @description: 电影信息相关接口
 */
@Api(value = "电影相关信息接口")
@Slf4j
@RestController
@RequestMapping("/app/movie")
public class MovieController {

    @Resource
    private MovieManager movieManager;

    @AuthToken
    @ApiOperation(value = "返回一部电影的具体信息")
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public RpcResult get(@ModelAttribute() @Validated MovieInfoQuery query) {
        return RpcResult.successResult(movieManager.get());
    }

}
