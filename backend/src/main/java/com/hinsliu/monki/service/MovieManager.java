package com.hinsliu.monki.service;

import com.hinsliu.monki.common.UtilConstant;
import com.hinsliu.monki.common.enums.ErrorCodeEnum;
import com.hinsliu.monki.common.exception.BusinessException;
import com.hinsliu.monki.common.utils.copy.CopyUtils;
import com.hinsliu.monki.common.utils.time.DateTimeUtil;
import com.hinsliu.monki.dal.ClickActionDao;
import com.hinsliu.monki.domain.model.ClickActionDO;
import com.hinsliu.monki.domain.model.MovieDO;
import com.hinsliu.monki.domain.query.MovieInfoQuery;
import com.hinsliu.monki.domain.view.MovieDTO;
import com.hinsliu.monki.domain.view.MovieMetaDTO;
import com.hinsliu.monki.domain.view.MusicDTO;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author liuxuanming
 * @date 2021/7/12 15:56
 * @description: 电影信息相关服务
 */
@Slf4j
@Service
public class MovieManager extends BaseManager {

    @Resource
    private ClickActionDao clickActionDao;

    @Resource
    private MongoTemplate mongoTemplate;

    public MovieDTO get(MovieInfoQuery query) {
        String id = query.getId();
        MovieDO movieDO = getOneMovieDocument(id);

        // 记录用户点击行为
        if (getUserId() != null) {
            saveClickAction(movieDO);
            log.info("记录用户{}的点击行为{}", getUserId(), movieDO.getName());
        }
        return CopyUtils.MovieDOToMovieDTO(movieDO);
    }

    private void saveClickAction(MovieDO movieDO) {
        ClickActionDO clickActionDO = new ClickActionDO();
        clickActionDO.setUid(getUserId());
        clickActionDO.setMid(movieDO.getId());
        clickActionDO.setName(movieDO.getName());
        clickActionDO.setGenre(String.join(UtilConstant.DEFAULT_DELIMITER, movieDO.getGenre()));
        clickActionDO.setTime(DateTimeUtil.getCurrentDateTime());

        int affectCount = clickActionDao.insert(clickActionDO);
        if (affectCount > 0) {
            log.info("插入用户点击记录，点击文档{}: {}", movieDO.getId(), affectCount);
        } else {
            log.warn("插入用户点击记录失败，点击文档{}", movieDO.getId());
            throw new BusinessException(ErrorCodeEnum.FAIL.getCode(), "插入用户点击记录失败");
        }
    }

    public MovieDO getOneMovieDocument(String id) {
        Query query = Query.query(Criteria.where("_id").is(new ObjectId(id)));

        MovieDO movieDO = mongoTemplate.findOne(query, MovieDO.class);
        if (movieDO == null) {
            log.warn("查找索引{}对应的电影文档失败", id);
            throw new BusinessException(ErrorCodeEnum.FAIL.getCode(), "查找索引对应的电影文档失败");
        } else {
            log.info("查找到索引{}对应的电影文档：{}", id, movieDO.getName());
        }
        return movieDO;
    }

    public MovieMetaDTO getMovieMeta(MovieDO movieDO) {
        MovieDTO movieDTO = CopyUtils.MovieDOToMovieDTO(movieDO);
        return CopyUtils.MovieDTOToMovieMetaDTO(movieDTO);
    }

}
