package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Member;
import org.example.demo05.entity.bean.MemberBean;
import org.example.demo05.utils.AuditEntity;

import java.util.List;

public interface MemberService {
    List<Member> getMembers(Page<?> page, MemberBean memberBean);

    Integer addMember(Member member);

    Integer updateMember(Member member);

    Integer deleteMember(AuditEntity auditEntity, Integer[] ids);
}
