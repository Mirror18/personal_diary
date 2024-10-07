package com.mirror.form.login;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author mirror
 */
@Data
public class PhoneRegisterForm {
    /**
     * 客户端id
     */
    // @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$",message = "手机号格式错误！")
    private String phone;

    //  @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\\\W]{6,18}$",message = "密码长度需在6~18位字符，且必须包含字母和数字！")
    private String password;

    //   @ApiModelProperty(value = "确认密码")
    @NotBlank(message = "请输入确认密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\\\W]{6,18}$",message = "密码长度需在6~18位字符，且必须包含字母和数字！")
    private String confirmPassword;

    //  @ApiModelProperty(value = "短信验证码")
    @NotBlank(message = "请输入短信验证码")
    @Pattern(regexp = "^[0-9]{6}$",message = "短信验证码格式不正确")
    private String smsCode;
}
