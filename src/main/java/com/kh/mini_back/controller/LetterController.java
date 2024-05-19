package com.kh.mini_back.controller;

import com.kh.mini_back.dao.LetterDAO;
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
public class LetterController {
    @GetMapping("/searchid")
    public ResponseEntity<List<UserInfoVO>> searchId(@RequestParam String id) {
        LetterDAO letterDAO = new LetterDAO();
        List<UserInfoVO> list = letterDAO.getId(id);
        return ResponseEntity.ok(list);
    }
}
