package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.demo05.dao.MemberDAO;
import org.example.demo05.entity.Member;
import org.example.demo05.entity.bean.MemberBean;
import org.example.demo05.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImplement implements MemberService {
    MemberDAO memberDAO;

    @Autowired
    public void setMemberMapper(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public List<Member> getMembers(Page<?> page, MemberBean memberBean) {
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            return memberDAO.selectAll(memberBean);
        }
    }

    @Override
    public Integer addMember(Member member) {
        return memberDAO.insert(member);
    }

    @Override
    public Integer updateMember(Member member) {
        return memberDAO.updateByPrimaryKey(member);
    }

    @Override
    public Integer deleteMember(Integer[] ids) {
        return memberDAO.deleteByPrimaryKey(ids);
    }
}
