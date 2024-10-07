package com.mirror.form.login;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author mirror
 */
@Data
public class SaveSendSmsCodeTemplateConfigForm {
    //  @ApiModelProperty(value = "配置key")
    @NotBlank
    @Pattern(regexp = "^REG|LOGIN|UPDATE_PHONE$",message = "短信验证码类型非法")
    private String configKey;

    //  @ApiModelProperty(value = "配置名称")
    @NotBlank
    private String configName;

    //   @ApiModelProperty(value = "短信签名")
    @NotBlank
    private String signName;

    //  @ApiModelProperty(value = "短信模板编号")
    @NotBlank
    private String templateCode;
}
