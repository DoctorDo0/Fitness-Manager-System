package org.example.demo05.service.implement;

import jakarta.annotation.Resource;
import org.example.demo05.dao.MemberDAO;
import org.example.demo05.entity.Member;
import org.example.demo05.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImplement implements MemberService {
    MemberDAO memberDAO;

    @Autowired
    public void setMemberMapper(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public List<Member> getMembers() {
        return memberDAO.selectAll();
    }

    @Override
    public Map<Boolean, String> addMember(Member member) {
        try {
            memberDAO.insert(member);
            return Map.of(true, "success");
        } catch (Exception e) {
            return Map.of(false, e.getMessage());
        }
    }

    @Override
    public Map<Boolean, String> updateMember(Member member) {
        try {
            memberDAO.updateByPrimaryKey(member);
            return Map.of(true, "success");
        } catch (Exception e) {
            return Map.of(false, e.getMessage());
        }
    }

    @Override
    public Integer deleteMember(Integer[] ids) {
        return memberDAO.deleteByPrimaryKey(ids);
    }
}
