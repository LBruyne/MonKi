package com.hinsliu.monki.domain.common;

import com.hinsliu.monki.common.UtilConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: When RPC involves a list of data, the request should contain page and pageSize parameters.
 * @author: liuxuanming
 * @date: 2021/03/23 3:03 下午
 */
@Data
@NoArgsConstructor
public class PageParam implements Serializable {

    private Integer page;

    private Integer pageSize;

    private Integer offset;

    public void setOffset() {
        if(getPage() != null && getPageSize() != null) {
            setOffset((getPage() - 1) * getPageSize());
        }
        else {
            setOffset(0);
        }
    }

    public static void verify(PageParam pageParam) {
        if (pageParam.getPage() == null || pageParam.getPage() <= 0) {
            pageParam.setPage(UtilConstant.DEFAULT_PAGE);
        }
        if (pageParam.getPageSize() == null || pageParam.getPageSize() <= 0) {
            pageParam.setPageSize(UtilConstant.DEFAULT_PAGE_SIZE);
        }
        if (pageParam.getPageSize() > UtilConstant.MAX_PAGE_SIZE) {
            pageParam.setPageSize(UtilConstant.MAX_PAGE_SIZE);
        }
        if (pageParam.getOffset() == null) {
            pageParam.setOffset();
        }
    }
}
