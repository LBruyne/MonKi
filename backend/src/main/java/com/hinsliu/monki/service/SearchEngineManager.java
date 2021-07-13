package com.hinsliu.monki.service;

import com.hinsliu.monki.common.enums.SearchTypeEnum;
import com.hinsliu.monki.domain.common.Page;
import com.hinsliu.monki.domain.common.PageParam;
import com.hinsliu.monki.domain.query.RecommendQuery;
import com.hinsliu.monki.domain.query.SearchQuery;
import com.hinsliu.monki.domain.view.MovieMetaDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private MovieManager movieManager;

    public Page<MovieMetaDTO> search(SearchQuery query) {
        // 分页验证
        PageParam.verify(query);

        String kw = query.getKeyword();
        SearchTypeEnum type = SearchTypeEnum.getType(query.getType());
        Integer page = query.getPage();
        Integer pageSize = query.getPageSize();
        Integer offset = query.getOffset();

        // 通过ElasticSearch进行搜索，
        // 得到排名靠前结果对应的索引集合
        SearchResult result = searchES(kw, type, offset, pageSize);

        // 根据每个索引，找到对应的文档记录并返回;
        return new Page<>(page, pageSize, result.getCount(), searchMongo(result.getResults()));
    }

    public Page<MovieMetaDTO> recommend(RecommendQuery query) {
        // 分页验证
        PageParam.verify(query);
        return null;
    }

    public SearchResult searchES(String kw, SearchTypeEnum type, Integer from, Integer size) {
        return null;
    }

    public List<MovieMetaDTO> searchMongo(List<String> ids) {
        return ids.stream().map(id -> movieManager.getMovieMeta(movieManager.getOneMovieDocument(id))).collect(Collectors.toList());
    }

    @Data
    private static class SearchResult {

        private List<String> results;

        private Integer count;

    }

}
