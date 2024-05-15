package com.kh.mini_back.controller;

import com.kh.mini_back.dao.LoginDAO;
import com.kh.mini_back.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class LoginController {
    //로그인 정보가 있으면 true
    @PostMapping("/login")
    public ResponseEntity<Boolean> getMember(@RequestBody UserInfoVO userInfoVO) {
        LoginDAO loginDAO = new LoginDAO();
        boolean isLogin = loginDAO.logIn(userInfoVO.getId(), userInfoVO.getPw());
        return ResponseEntity.ok(isLogin);
    }

    //이메일로 아이디 비밀번호 찾기
    @PostMapping("/certemail")
    public ResponseEntity<String> findMemberEmail(@RequestBody Map<String,String> findData) {
        LoginDAO loginDAO = new LoginDAO();
        String email = findData.get("email");
        String category = findData.get("category");
        String find = loginDAO.findInfo(email,category);
        return ResponseEntity.ok(find);
    }

    //아이디가 존재하면 true
    @GetMapping("/conid")
    public ResponseEntity<Boolean> findMemberId(@RequestParam String id) {
        LoginDAO loginDAO = new LoginDAO();
        Boolean conId = loginDAO.checkId(id);
        return ResponseEntity.ok(conId);
    }

    //이메일이 존재하면 true
    @GetMapping("/conemail")
    public ResponseEntity<Boolean> findMemberEmail(@RequestParam String email) {
        LoginDAO loginDAO = new LoginDAO();
        Boolean conEmail = loginDAO.checkEmail(email);
        return ResponseEntity.ok(conEmail);
    }
    //회원가입 성공하면 true
    @PostMapping("/signup")
    public ResponseEntity<Boolean> memberReg(@RequestBody UserInfoVO userInfoVo) {
        LoginDAO loginDAO = new LoginDAO();
        Boolean isSignUp = loginDAO.SignUp(userInfoVo);
        return ResponseEntity.ok(isSignUp);
    }
}
