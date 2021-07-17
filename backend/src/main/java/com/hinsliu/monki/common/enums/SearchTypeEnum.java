package com.hinsliu.monki.common.enums;

/**
 * @author liuxuanming
 * @date 2021/7/12 16:23
 * @description: 搜索类别枚举
 */
public enum SearchTypeEnum {

    DEFAULT("混合", 0),

    MOVIE("电影", 1),

    LOCATION("地点", 2),

    MUSIC("音乐", 3),

    ;

    private final String name;

    private final Integer val;

    SearchTypeEnum(String name, Integer val) {
        this.name = name;
        this.val = val;
    }

    public static SearchTypeEnum getType(Integer val) {
        for (SearchTypeEnum type : SearchTypeEnum.values()) {
            if (type.getVal().equals(val)) {
                return type;
            }
        }
        return DEFAULT;
    }

    public String getName() {
        return name;
    }

    public Integer getVal() {
        return val;
    }
}
