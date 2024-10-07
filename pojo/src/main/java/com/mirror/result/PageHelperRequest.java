package com.mirror.result;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author mirror
 */
@Data
public class PageHelperRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1604684240674386686L;
    //    @Schema(name = "pageNum", description = "当前页, 默认1")
    @NotNull(message = "当前页不能为空")
    @Min(value = 1)
    private Integer pageNum = 1;

    //    @Schema(name = "pageSize", description = "每页记录数, 默认10")
    @NotNull(message = "每页记录数不能为空")
    private Integer pageSize = 10;

    /**
     * 获取对应数据库的第几行记录
     * @return 数据库数据行数的计算方式
     */
    public Integer getOffset() {
        return this.pageNum > 0 ? ((this.pageNum - 1) * this.pageSize) : 0;
    }
}
