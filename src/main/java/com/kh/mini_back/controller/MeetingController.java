package com.kh.mini_back.controller;



import com.kh.mini_back.dao.MeetingDAO;
import com.kh.mini_back.vo.ChatVO;
import com.kh.mini_back.vo.MeetingMemberVO;
import com.kh.mini_back.vo.MeetingVO;
import com.kh.mini_back.vo.ScheduleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class MeetingController {
    //모집하기
    @PostMapping("/recruit")
    public ResponseEntity<Boolean> recruit(@RequestBody MeetingVO meetingVO) {
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean isRecruit = meetingDAO.recruit(meetingVO);
        return ResponseEntity.ok(isRecruit);
    }
    //채팅 리스트
    @GetMapping("/meetinglist")
    public ResponseEntity<List<MeetingVO>> meetingList() {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<MeetingVO> list = meetingDAO.meetingList();
        return ResponseEntity.ok(list);
    }
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
    @GetMapping("/memberlist")
    public ResponseEntity<List<MeetingMemberVO>> memberList(@RequestParam int meetingNo) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<MeetingMemberVO> list = meetingDAO.memberList(meetingNo);
        return ResponseEntity.ok(list);
    }

    //스케쥴 list
    @GetMapping("/schedulelist")
    public ResponseEntity<List<ScheduleVO>> scheduleList(@RequestParam int meetingNo) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<ScheduleVO> list = meetingDAO.scheduleList(meetingNo);
        return ResponseEntity.ok(list);
    }
    @PostMapping("/writerlist")
    public ResponseEntity<Set<ScheduleVO>> writerList(@RequestBody Map<String,String> data) {
        MeetingDAO meetingDAO = new MeetingDAO();
        int mno = Integer.parseInt(data.get("mno"));
        int year = Integer.parseInt(data.get("year"));
        int month = Integer.parseInt(data.get("month"));

        Set <ScheduleVO> set = meetingDAO.writerList(mno, year, month);
        return ResponseEntity.ok(set);
    }
    @GetMapping("/mainsersel")
    public ResponseEntity<List<MeetingVO>> mainsersel(@RequestParam String searchType, @RequestParam String keyword) {
        MeetingDAO dao = new MeetingDAO();
        List<MeetingVO> list = dao.mainsel(searchType, keyword);
        System.out.println(searchType);
        System.out.println(keyword);
        return ResponseEntity.ok(list);
    }
}
