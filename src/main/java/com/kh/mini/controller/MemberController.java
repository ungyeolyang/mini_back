package com.kh.mini.controller;

import com.kh.mini.dao.MemberDAO;
import com.kh.mini.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@Slf4j // 로그 뽑아내기위해 사용
public class MemberController {
    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> getMember(@RequestParam String name) {
        log.debug("회원 이름으로 조회 : " + name);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.memberSelect(name);
        return ResponseEntity.ok(list);
    }

//    @PostMapping("/new")
//    public ResponseEntity<Boolean> memberReg(@RequestBody Map<String, String> regData) {
//        String getId = regData.get("id"); // 맵에서 value 뽑을때 get 사용
//        String getPwd = regData.get("pwd");
//        String getName = regData.get("name");
//        String getMail = regData.get("mail");
//        MemberDAO dao = new MemberDAO();
//        boolean isTrue = dao.memberRegister(getId, getPwd, getName, getMail);
//        return ResponseEntity.ok(isTrue);
//    }

    @PostMapping("/new")
    public ResponseEntity<Boolean> memberReg(@RequestBody MemberVO member) {
        //map으로 받아도되는데 vo로 받는것이 더간단하다. 대신 vo 인스턴스변수가 객체의 키와 같아야한다.
        MemberDAO dao = new MemberDAO();
        boolean isTrue = dao.memberRegister(member);
        return ResponseEntity.ok(isTrue);
    }
}
