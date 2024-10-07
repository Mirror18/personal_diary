package com.mirror.service.login;

import com.mirror.entity.member.MemberBindPhone;
import com.mirror.form.login.UpdatePasswordForm;
import com.mirror.form.login.UpdatePhoneForm;

/**
 * @author mirror
 */
public interface MemberBindPhoneService {
    /**
     * 根据手机号获取用户信息
     *
     * @param phone
     * @return
     */
    MemberBindPhone getMemberByPhone(String phone);

    /**
     * 手机号注册
     *
     * @param phone
     * @param memberId
     * @param password
     * @return
     */
    boolean reg(String phone, long memberId, String password);

    /**
     * 修改密码
     *
     * @param form
     * @return
     */
    boolean updatePassword(UpdatePasswordForm form);

    /**
     * 获取手机账号信息
     * @param memberId
     * @return
     */
    MemberBindPhone getById(long memberId);

    /**
     * 修改手机号
     * @param form
     * @return
     */
    boolean updatePhone(UpdatePhoneForm form);
}
