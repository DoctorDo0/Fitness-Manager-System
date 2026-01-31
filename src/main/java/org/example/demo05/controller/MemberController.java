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
            return JsonResp.error(500, e.toString());
        }
    }

    @PostMapping
    public JsonResp addMember(@RequestBody Member member) {
        try {
            int resp = memberService.addMember(member);
            return JsonResp.success(resp);
        } catch (Exception e) {
            return JsonResp.error(500, e.toString());
        }
    }

    @PutMapping
    public JsonResp updateMember(@RequestBody Member member) {
        try {
            int resp = memberService.updateMember(member);
            return JsonResp.success(resp);
        } catch (Exception e) {
            return JsonResp.error(500, e.toString());
        }
    }

    @DeleteMapping
    public JsonResp deleteMember(Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int res = memberService.deleteMember(ids);
            return JsonResp.success(res);
        } catch (Exception e) {
            return JsonResp.error(500, e.toString());
        }
    }
}
