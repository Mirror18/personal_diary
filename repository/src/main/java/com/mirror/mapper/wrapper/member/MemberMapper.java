package com.mirror.mapper.wrapper.member;

import com.mirror.entity.member.Member;
import com.mirror.mapper.wrapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mirror
 */
@Mapper
public interface MemberMapper extends CommonMapper<Member> {
}