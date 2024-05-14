package com.kh.mini_back.controller;

import com.kh.mini_back.dao.MemberDAO;
import com.kh.mini_back.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class MemberController {
    //로그인 정보가 있으면 true
    @PostMapping("/login")
    public ResponseEntity<Boolean> getMember(@RequestBody MemberVO memberVO) {
        MemberDAO memberDAO = new MemberDAO();
        boolean isLogin = memberDAO.logIn(memberVO.getId(),memberVO.getPw());
        return ResponseEntity.ok(isLogin);
    }

    //이메일로 아이디 비밀번호 찾기
    @PostMapping("/certEmail")
    public ResponseEntity<String> findMemberEmail(@RequestBody Map<String,String> findData) {
        MemberDAO memberDAO = new MemberDAO();
        String email = findData.get("email");
        String category = findData.get("category");
        String find = memberDAO.findInfo(email,category);
        return ResponseEntity.ok(find);
    }

    //아이디가 존재하면 true
    @GetMapping("/conId")
    public ResponseEntity<Boolean> findMemberId(@RequestParam String id) {
        MemberDAO memberDAO = new MemberDAO();
        Boolean conId = memberDAO.checkId(id);
        return ResponseEntity.ok(conId);
    }

    //이메일이 존재하면 true
    @GetMapping("/conEmail")
    public ResponseEntity<Boolean> findMemberEmail(@RequestParam String email) {
        MemberDAO memberDAO = new MemberDAO();
        Boolean conEmail = memberDAO.checkEmail(email);
        return ResponseEntity.ok(conEmail);
    }

    @PostMapping("/new")
    public ResponseEntity<Boolean> memberReg(@RequestBody MemberVO member) {
        //map으로 받아도되는데 vo로 받는것이 더간단하다. 대신 vo 인스턴스변수가 객체의 키와 같아야한다.
        MemberDAO dao = new MemberDAO();
        return null;
    }
}
