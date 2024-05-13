package com.kh.mini.controller;

import com.kh.mini.vo.MemberVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api")// 공통주소
public class TestController {
    @GetMapping("/name")
    public String getName() {
        return "Get방식이고, 이름은 곰돌이 사육사 입니다.";
    }
    @GetMapping("/variable/{var}")
    public String getVar(@PathVariable String var){
        return "요청하신 페이지 번호는 " + var + "입니다.";
    }

    @GetMapping("/request")
    public String getRequestParam(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String company
    ){
        return name + " " + email + " " + company;
    }

    @GetMapping("/members")
    public List<Map<String, Object>> findMembers() {
        List<Map<String,Object>> members = new ArrayList<>();
        for(int i = 0; i <= 20; i++) {
            Map<String,Object> member = new HashMap<>();
            member.put("id", i);
            member.put("name", i+"번째 개발자");
            member.put("age", 10+i);
            members.add(member);
        }
        return members;
    }

    @GetMapping("/members2")
    public ResponseEntity<List<MemberVO>> listMembers() {
        List<MemberVO> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            MemberVO vo = new MemberVO();
            vo.setId("yuy" + i);
            vo.setName("양" + i);
            vo.setPwd("웅" + i);
            vo.setEmail("열" + i + "@naver.com");

            list.add(vo);
        }
        return ResponseEntity.ok(list);
    }
}
