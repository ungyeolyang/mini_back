package com.kh.mini_back.controller;

import com.kh.mini_back.dao.MemberDAO;
import com.kh.mini_back.vo.MemberVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class MemberController {
    @GetMapping("/member")
    public ResponseEntity<Boolean> getMember(@RequestParam String name) {
        MemberDAO memberDAO = new MemberDAO();
        return null;
    }


    @PostMapping("/new")
    public ResponseEntity<Boolean> memberReg(@RequestBody MemberVO member) {
        //map으로 받아도되는데 vo로 받는것이 더간단하다. 대신 vo 인스턴스변수가 객체의 키와 같아야한다.
        MemberDAO dao = new MemberDAO();
        return null;
    }
}
