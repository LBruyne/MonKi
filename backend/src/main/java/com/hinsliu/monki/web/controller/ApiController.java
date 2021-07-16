package com.hinsliu.monki.web.controller;

import com.hinsliu.monki.domain.common.RpcResult;
import com.hinsliu.monki.domain.query.UserLoginQuery;
import com.hinsliu.monki.service.MovieManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuxuanming
 * @date 2021/7/16 11:25
 * @description: API接口
 */
@Api(description = "内部API相关接口")
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    private MovieManager movieManager;

    @ApiOperation(value = "解析电影信息，创建便于查询的形式")
    @RequestMapping(value = "/movie/parse", method = {RequestMethod.POST, RequestMethod.GET})
    public RpcResult parseMovieData() {
        movieManager.parseMongoMovies();
        return RpcResult.successResult();
    }

}
