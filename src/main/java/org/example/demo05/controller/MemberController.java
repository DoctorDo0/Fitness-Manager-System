package org.example.demo05.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.example.demo05.entity.Member;
import org.example.demo05.entity.bean.MemberBean;
import org.example.demo05.service.implement.MemberServiceImplement;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {
    MemberServiceImplement memberService;

    @Autowired
    public void setMemberService(MemberServiceImplement memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public JsonResp getMembers(int pageNo, int pageSize, MemberBean memberBean) {
        try {
            Page<?> page = new Page<>(pageNo, pageSize);
            List<Member> members = memberService.getMembers(page, memberBean);
            PageInfo<?> pageInfo = new PageInfo<>(members);
            return JsonResp.success(pageInfo);
        } catch (Exception e) {
            return JsonResp.error(e.toString());
        }
    }

    @PostMapping
    public Map<Boolean, String> addMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }

    @PutMapping
    public Map<Boolean, String> updateMember(@RequestBody Member member) {
        return memberService.updateMember(member);
    }

    @DeleteMapping
    public Integer deleteMember(Integer[] ids) {
        return memberService.deleteMember(ids);
    }
}
