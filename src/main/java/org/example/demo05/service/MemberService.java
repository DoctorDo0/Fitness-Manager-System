package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Member;
import org.example.demo05.entity.bean.MemberBean;

import java.util.List;
import java.util.Map;

public interface MemberService {
    List<Member> getMembers(Page<?>page, MemberBean memberBean);

    Map<Boolean, String> addMember(Member member);

    Map<Boolean, String> updateMember(Member member);

    Integer deleteMember(Integer[] ids);
}
