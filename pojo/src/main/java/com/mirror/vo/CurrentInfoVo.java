package com.mirror.vo;

import lombok.Data;

/**
 * @author mirror
 */
@Data
public class CurrentInfoVo {
    //    @ApiModelProperty(value = "昵称")
    private String name;
    //    @ApiModelProperty(value = "头像")
    private String avatar;
    //    @ApiModelProperty(value = "邮箱地址")
    private String email;
}