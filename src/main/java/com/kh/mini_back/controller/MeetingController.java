package com.kh.mini_back.controller;



import com.kh.mini_back.dao.FriendDAO;
import com.kh.mini_back.dao.LetterDAO;
import com.kh.mini_back.dao.MeetingDAO;
import com.kh.mini_back.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://192.168.10.13:3000")
//@CrossOrigin(origins = "http://localhost:3000")
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
    //모집하고 나서 모임장 참여리스트에 넣기
    @PostMapping("/master")
    public ResponseEntity<Boolean> master(@RequestBody MeetingVO meetingVO) {
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean isMaster = meetingDAO.master(meetingVO);
        return ResponseEntity.ok(isMaster);
    }

    //신청하기
    @PostMapping("/application")
    public ResponseEntity<Boolean> application(@RequestBody MeetingVO meetingVO) {
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean isMaster = meetingDAO.application(meetingVO);
        return ResponseEntity.ok(isMaster);
    }
    //모임 수락
    @PostMapping("/acceptok")
    public ResponseEntity<Boolean> acceptOk(@RequestBody MeetingMemberVO meetingMemberVO) {
        MeetingDAO meetingDAO = new MeetingDAO();
        Boolean isUpdate = meetingDAO.acceptOk(meetingMemberVO);
        return ResponseEntity.ok(isUpdate);
    }
    //멤버 삭제
    @PostMapping("/delmember")
    public ResponseEntity<Boolean> delMember(@RequestBody MeetingMemberVO meetingMemberVO) {
        MeetingDAO meetingDAO = new MeetingDAO();
        Boolean isDelete = meetingDAO.delMember(meetingMemberVO);
        return ResponseEntity.ok(isDelete);
    }
    //모임 신청 여부확인
    @GetMapping("/conaccept")
    public ResponseEntity<List<Integer>> conAccept(@RequestParam String id) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<Integer> list = meetingDAO.conAccept(id);
        return ResponseEntity.ok(list);
    }
    //내 모임 리스트
    @GetMapping("/mymeetinglist")
    public ResponseEntity<List<MeetingVO>> myMeetingList(@RequestParam String myId) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<MeetingVO> list = meetingDAO.myMeetingList(myId);
        return ResponseEntity.ok(list);
    }
    //모임정보
    @GetMapping("/meetinginfo")
    public ResponseEntity<List<MeetingVO>> meetingInfo(@RequestParam int no) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<MeetingVO> list = meetingDAO.meetingInfo(no);
        return ResponseEntity.ok(list);
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
    public ResponseEntity<List<ChatVO>> chatList(@RequestParam int no) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<ChatVO> list = meetingDAO.chatList(no);
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
    @GetMapping("/delschedule")
    public ResponseEntity<Boolean> delFriend(@RequestParam int sno) {
        MeetingDAO meetingDAO = new MeetingDAO();
        Boolean isDelete = meetingDAO.delSchedule(sno);
        return ResponseEntity.ok(isDelete);
    }
     //모임 신청자 list
    @GetMapping("/acceptlist")
    public ResponseEntity<List<MeetingMemberVO>> acceptList(@RequestParam String id) {
        MeetingDAO meetingDAO = new MeetingDAO();
        List<MeetingMemberVO>  list = meetingDAO.acceptList(id);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/mainsersel")
    public ResponseEntity<List<MeetingVO>> mainsersel(@RequestParam String searchType, @RequestParam String keyword) {
        MeetingDAO dao = new MeetingDAO();
        List<MeetingVO> list = dao.mainsel(searchType, keyword);
        System.out.println(searchType);
        System.out.println(keyword);
        return ResponseEntity.ok(list);
    }
    //일정쓰기
    @PostMapping("/sendschedule")
    public ResponseEntity<Boolean> sendSchedule(@RequestBody ScheduleVO scheduleVO) {
        MeetingDAO meetingDAO = new MeetingDAO();
        Boolean isSend = meetingDAO.send(scheduleVO);
        return ResponseEntity.ok(isSend);
    }
}
