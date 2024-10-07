package com.mirror.controller;

import com.mirror.form.GetBase64CodeForm;
import com.mirror.form.login.GetClientTokenForm;
import com.mirror.form.login.GetSmsCodeForm;
import com.mirror.form.login.PhonePasswordLoginForm;
import com.mirror.form.login.PhoneSmsCodeLoginForm;
import com.mirror.result.ApiResponse;
import com.mirror.result.TokenResponse;
import com.mirror.service.login.MemberLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author mirror
 */ //@Api(tags = "用户登录模块")
@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    final MemberLoginService memberLoginService;

    //  @ApiOperation(value = "获取客户端Id")
    @GetMapping(value = "/getClientId")
    public ApiResponse<String> getClientId() {
        String result = memberLoginService.getClientId();
        return ApiResponse.success(result);
    }

    // @ApiOperation(value = "获取图形验证码")
    @GetMapping(value = "/getBase64Code")
    public ApiResponse<String> getBase64Code(@Validated @ModelAttribute GetBase64CodeForm form) {
        String code = memberLoginService.getBase64Code(form);
        return ApiResponse.success(code);
    }

    //  @ApiOperation(value = "获取短信验证码")
    @GetMapping(value = "/sendSmsCode")
    public ApiResponse<Void> sendSmsCode(@Validated @ModelAttribute GetSmsCodeForm form) {
        memberLoginService.sendSmsCode(form);
        return ApiResponse.success();
    }

    // @ApiOperation(value = "手机密码登录")
    @PostMapping(value = "/phonePasswordLogin")
    public ApiResponse<TokenResponse> phonePasswordLogin(@Validated @RequestBody PhonePasswordLoginForm form) {
        TokenResponse tokenResponse = memberLoginService.phonePasswordLogin(form);
        return ApiResponse.success(tokenResponse);
    }

    //  @ApiOperation(value = "手机短信登录")
    @PostMapping(value = "/phoneSmsCodeLogin")
    public ApiResponse<TokenResponse> phoneSmsCodeLogin(@Validated @RequestBody PhoneSmsCodeLoginForm request) {
        TokenResponse tokenResponse = memberLoginService.phoneSmsCodeLogin(request);
        return ApiResponse.success(tokenResponse);
    }

    // @ApiOperation(value = "获取客户端token")
    @GetMapping(value = "/getClientToken")
    public ApiResponse<TokenResponse> getClientToken(@Validated @ModelAttribute GetClientTokenForm request) {
        TokenResponse result = memberLoginService.getClientToken(request.getClientId());
        return ApiResponse.success(result);
    }
}
