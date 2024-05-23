package com.kh.mini_back.controller;

import com.kh.mini_back.dao.FriendDAO;
import com.kh.mini_back.dao.LoginDAO;
import com.kh.mini_back.dao.MeetingDAO;
import com.kh.mini_back.vo.ChatVO;
import com.kh.mini_back.vo.FriendVO;
import com.kh.mini_back.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class MeetingController {
    //채팅 쓰기
    @PostMapping("/chat")
    public ResponseEntity<Boolean> chat(@RequestBody ChatVO chatVO) {
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean isLogin = meetingDAO.chat(chatVO);
        return ResponseEntity.ok(isLogin);
    }
    //채팅 리스트
    @GetMapping("/chatlist")
    public ResponseEntity<List<ChatVO>> chatList(@RequestParam int meetingNo) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<ChatVO> list = meetingDAO.chatList(meetingNo);
        return ResponseEntity.ok(list);
    }

    //모임 참여자 list
    @GetMapping("/moimlist")
    public ResponseEntity<List<FriendVO>> sendList(@RequestParam int meetingNo) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<meetingVO> list = meetingDAO.chatList(meetingNo);
        return ResponseEntity.ok(list);
    }
}
