package com.mirror.form.login;


import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author mirror
 */
@Data
public class UpdatePasswordForm {
    //  @ApiModelProperty(value = "旧密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\\\W]{6,18}$",message = "密码长度需在6~18位字符，且必须包含字母和数字！")
    @NotBlank
    private String oldPassword;

    //   @ApiModelProperty(value = "新密码")
    @NotBlank(message = "请输入新密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\\\W]{6,18}$",message = "密码长度需在6~18位字符，且必须包含字母和数字！")
    private String password;

    //   @ApiModelProperty(value = "确认新密码")
    @NotBlank(message = "请确认新密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\\\W]{6,18}$",message = "密码长度需在6~18位字符，且必须包含字母和数字！")
    private String confirmPassword;
}
