package com.hinsliu.monki.common.utils.copy;

import com.hinsliu.monki.domain.model.LocationDO;
import com.hinsliu.monki.domain.model.MovieDO;
import com.hinsliu.monki.domain.model.MusicDO;
import com.hinsliu.monki.domain.view.LocationDTO;
import com.hinsliu.monki.domain.view.MovieDTO;
import com.hinsliu.monki.domain.view.MusicDTO;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author liuxuanming
 * @date 2021/7/13 11:16
 * @description: 属性赋值公共类
 */
public class CopyUtils {

    public static MovieDTO MovieDOToMovieDTO(MovieDO movieDO) {
        MovieDTO movieDTO = new MovieDTO();
        BeanUtils.copyProperties(movieDO, movieDTO);
        movieDTO.setLocation(LocationDOToLocationDTO(movieDO.getLocation()));
        movieDTO.setMusic(movieDO.getMusic().stream().map(CopyUtils::MusicDOToMusicDTO).collect(Collectors.toList()));
        return movieDTO;
    }

    private static MusicDTO MusicDOToMusicDTO(MusicDO musicDO) {
        MusicDTO musicDTO = new MusicDTO();
        BeanUtils.copyProperties(musicDO, musicDTO);
        return musicDTO;
    }

    private static LocationDTO LocationDOToLocationDTO(LocationDO locationDO) {
        LocationDTO locationDTO = new LocationDTO();
        BeanUtils.copyProperties(locationDO, locationDTO);
        return locationDTO;
    }

}
