package org.example.demo05.service;

import org.example.demo05.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    List<Member> getMembers();

    Map<Boolean, String> addMember(Member member);

    Map<Boolean, String> updateMember(Member member);

    Integer deleteMember(Integer[] ids);
}
