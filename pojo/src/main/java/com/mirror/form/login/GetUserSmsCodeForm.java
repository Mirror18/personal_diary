package com.mirror.form.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * @author mirror
 */
@Validated
@Data
public class GetUserSmsCodeForm {
    //  @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$",message = "手机号格式错误！")
    private String phone;

    //  @ApiModelProperty(value = "图形验证码")
    @NotBlank(message = "请输入图形验证码")
    @Pattern(regexp = "^[a-zA-Z0-9]{5}$",message = "图形验证码格式不正确")
    private String code;

    //  @ApiModelProperty(value = "验证码类型 UPDATE_PHONE")
    @NotBlank(message = "请输入短信验证码类型")
    @Pattern(regexp = "^UPDATE_PHONE$",message = "短信验证码类型非法")
    private String smsCodeType;
}
