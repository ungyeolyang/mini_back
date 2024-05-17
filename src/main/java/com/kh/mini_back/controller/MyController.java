package com.kh.mini_back.controller;

import com.kh.mini_back.dao.LoginDAO;
import com.kh.mini_back.dao.MyDAO;
import com.kh.mini_back.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class MyController {
    @PostMapping("/memberedit")
    public ResponseEntity<Boolean> editInfo(@RequestBody UserInfoVO userInfoVO) {
        MyDAO myDAO = new MyDAO();
        boolean num = myDAO.editInfo(userInfoVO);
        return ResponseEntity.ok(num);
    }
}
