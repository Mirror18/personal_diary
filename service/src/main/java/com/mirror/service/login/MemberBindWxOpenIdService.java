package com.mirror.service.login;

import com.mirror.entity.member.MemberBindWxOpenId;

/**
 * @author mirror
 */
public interface MemberBindWxOpenIdService {
    /**
     * 判断appid和openid是否存在
     *
     * @param appid
     * @param openid
     * @return
     */
    MemberBindWxOpenId get(String appid, String openid);

    /**
     * 注册
     * @param appid
     * @param openid
     * @param memberId
     * @return
     */
    boolean reg(String appid, String openid, long memberId);
}
