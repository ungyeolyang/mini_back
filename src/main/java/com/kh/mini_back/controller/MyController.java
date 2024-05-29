package com.kh.mini_back.controller;

import com.kh.mini_back.dao.LoginDAO;
import com.kh.mini_back.dao.MyDAO;
import com.kh.mini_back.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://192.168.10.13:3000")
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class MyController {
    //멤버 수정성공하면 true
    @PostMapping("/memberedit")
    public ResponseEntity<Boolean> editInfo(@RequestBody UserInfoVO userInfoVO) {
        MyDAO myDAO = new MyDAO();
        boolean isMemEdit = myDAO.editInfo(userInfoVO);
        return ResponseEntity.ok(isMemEdit);
    }
    //프로필 수정성공하면 true
    @PostMapping("/profileedit")
    public ResponseEntity<Boolean> editProfile(@RequestBody UserInfoVO userInfoVO) {
        MyDAO myDAO = new MyDAO();
        boolean isProEdit = myDAO.editProfile(userInfoVO);
        return ResponseEntity.ok(isProEdit);
    }
    //접속한 아이디에 맞는 비밀번호이면 true
    @PostMapping("/conpw")
    public ResponseEntity<Boolean> findMemberPw(@RequestBody UserInfoVO userInfoVO) {
        MyDAO myDAO = new MyDAO();
        Boolean conPw = myDAO.checkPw(userInfoVO);
        return ResponseEntity.ok(conPw);
    }

}
