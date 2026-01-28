package org.example.demo05.controller;

import org.example.demo05.entity.Member;
import org.example.demo05.service.implement.MemberServiceImplement;
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
    public List<Member> getMembers() {
        return memberService.getMembers();
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
