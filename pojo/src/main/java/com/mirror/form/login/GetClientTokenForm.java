package com.mirror.form.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author mirror
 */
@Data
public class GetClientTokenForm {
    @NotBlank(message = "客户端ID不能为空")
    @Pattern(regexp="^[0-9A-Za-z]{6,32}$",message = "clientId非法")
    private String clientId;
}
