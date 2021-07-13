package com.hinsliu.monki.service;

import com.hinsliu.monki.domain.common.Page;
import com.hinsliu.monki.domain.view.MovieMetaDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:47
 * @description: 搜索引擎相关服务
 */
@Slf4j
@Service
public class SearchEngineManager extends BaseManager {

    @Autowired
    private RestHighLevelClient highLevelClient;

    public Page<MovieMetaDTO> search() {
        return null;
    }

    public Page<MovieMetaDTO> recommend() {
        return null;
    }

}
