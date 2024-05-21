package com.kh.mini_back.controller;

import com.kh.mini_back.dao.LetterDAO;
import com.kh.mini_back.vo.LetterVO;
import com.kh.mini_back.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class LetterController {
    //아이디를 입력받아 닉네임가져오기
    @GetMapping("/getnick")
    public ResponseEntity<String> getNick(@RequestParam String id) {
        LetterDAO letterDAO = new LetterDAO();
        String  nick = letterDAO.getNick(id);
        return ResponseEntity.ok(nick);
    }
    //아이디 검색
    @GetMapping("/searchid")
    public ResponseEntity<List<UserInfoVO>> searchId(@RequestParam String id) {
        LetterDAO letterDAO = new LetterDAO();
        List<UserInfoVO> list = letterDAO.getId(id);
        return ResponseEntity.ok(list);
    }
    @PostMapping("/send")
    public ResponseEntity<Boolean> sendLetter(@RequestBody LetterVO letterVo) {
        LetterDAO letterDAO = new LetterDAO();
        Boolean isSend = letterDAO.send(letterVo);
        return ResponseEntity.ok(isSend);
    }
    @PostMapping("/letterList")
    public ResponseEntity<List<LetterVO>> getLetterList(@RequestBody Map<String,String> data) {
        LetterDAO letterDAO = new LetterDAO();
        String id = data.get("id");
        String category = data.get("category");
        List<LetterVO> list = letterDAO.getLetter(id,category);
        return ResponseEntity.ok(list);
    }
    //읽으면 수신으로 변경
    @GetMapping("/setview")
    public ResponseEntity<Boolean> setView(@RequestParam String no) {
        LetterDAO letterDAO = new LetterDAO();
        Boolean isView = letterDAO.setView(no);
        return ResponseEntity.ok(isView);
    }


}
