package com.mirror.service.login.impl;

import com.mirror.constant.CommonConstant;
import com.mirror.convertor.ObjectConvertor;
import com.mirror.dto.AdminDTO;
import com.mirror.entity.member.Member;
import com.mirror.form.UpdateEmailAndNameForm;
import com.mirror.mapper.wrapper.MyBatisWrapper;
import com.mirror.mapper.wrapper.member.MemberMapper;
import com.mirror.service.TokenService;
import com.mirror.service.login.MemberService;
import com.mirror.vo.ListMemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.mirror.mapper.wrapper.member.Field.MemberField.*;

/**
 * @author mirror
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    final MemberMapper memberMapper;
    final TokenService<AdminDTO> tokenService;
    final ObjectConvertor objectConvertor;

    /**
     * 注册
     *
     * @param tenantId 租户id
     * @return 会员id
     */
    @Override
    public long reg(long tenantId) {
        Member member = new Member();
        member.setTenantId(tenantId);
        member.setSysRoleIds("[" + CommonConstant.ROLE_MEMBER + "]");
        member.initDefault();
        memberMapper.insert(member);
        return member.getId();
    }

    /**
     * 获取会员信息
     *
     * @param id
     * @return
     */
    @Override
    public Member get(long id) {
        MyBatisWrapper<Member> myBatisWrapper = new MyBatisWrapper<>();
        myBatisWrapper.select(NickName, Id, Disable, Name, AvatarUrl, SysRoleIds,
                        TenantId, Email)
                .whereBuilder().andEq(setId(id));
        return memberMapper.topOne(myBatisWrapper);
    }

    /**
     * 修改邮箱和姓名
     *
     * @param form
     * @return
     */
    @Override
    public boolean updateEmailAndName(UpdateEmailAndNameForm form) {
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        wrapper.update(setEmail(form.getEmail()))
                .update(setName(form.getName()))
                .whereBuilder()
                .andEq(setId(tokenService.getThreadLocalUserId()))
                .andEq(setDisable(false));
        return memberMapper.updateField(wrapper) > 0;
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @Override
    public List<ListMemberVo> listMember() {
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        wrapper.select(Id, NickName, Name)
                .whereBuilder()
                .andEq(TenantId, tokenService.getThreadLocalTenantId())
                .andEq(Disable, false);
        List<Member> members = memberMapper.list(wrapper);
        return objectConvertor.toListMemberVo(members);
    }
    /**
     * 根据id查询会员列表
     *
     * @param ids
     * @return
     */
    @Override
    public List<Member> listByIds(Set<Long> ids) {
        MyBatisWrapper<Member> wrapper = new MyBatisWrapper<>();
        wrapper.select(Id, NickName, Name)
                .whereBuilder()
                .andIn(Id, ids);
        return memberMapper.list(wrapper);
    }
}
