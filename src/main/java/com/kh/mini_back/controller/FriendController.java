package com.kh.mini_back.controller;

import com.kh.mini_back.dao.FriendDAO;
import com.kh.mini_back.dao.LetterDAO;
import com.kh.mini_back.vo.FriendVO;
import com.kh.mini_back.vo.LetterVO;
import com.kh.mini_back.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class FriendController {
    //친구 신청하기
    @PostMapping("/addfriend")
    public ResponseEntity<Boolean> addFriend(@RequestBody FriendVO friendVO) {
        FriendDAO friendDAO = new FriendDAO();
        Boolean isAdd = friendDAO.addFriend(friendVO);
        return ResponseEntity.ok(isAdd);
    }
    //친구 신청 여부확인
    @PostMapping("/consend")
    public ResponseEntity<Boolean> conSend(@RequestBody FriendVO friendVO) {
        FriendDAO friendDAO = new FriendDAO();
        Boolean isSend = friendDAO.conSend(friendVO);
        return ResponseEntity.ok(isSend);
    }
    //친구 여부확인
    @PostMapping("/confriend")
    public ResponseEntity<Boolean> conFriend(@RequestBody FriendVO friendVO) {
        FriendDAO friendDAO = new FriendDAO();
        Boolean isFriend = friendDAO.conFriend(friendVO);
        return ResponseEntity.ok(isFriend);
    }
    //친구 신청한 사람 list
    @GetMapping("/sendlist")
    public ResponseEntity<List<FriendVO>> sendList(@RequestParam String id) {
        FriendDAO friendDAO = new FriendDAO();
        List<FriendVO> list = friendDAO.sendList(id);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/friendOk")
    public ResponseEntity<Boolean> friendOk(@RequestBody FriendVO friendVO) {
        FriendDAO friendDAO = new FriendDAO();
        Boolean isUpdate = friendDAO.friendOk(friendVO);
        return ResponseEntity.ok(isUpdate);
    }

    @PostMapping("/delfriend")
    public ResponseEntity<Boolean> delFriend(@RequestBody FriendVO friendVO) {
        FriendDAO friendDAO = new FriendDAO();
        Boolean isDelete = friendDAO.delFriend(friendVO);
        return ResponseEntity.ok(isDelete);
    }

    //내 친구  list
    @GetMapping("/friendlist")
    public ResponseEntity<TreeSet<FriendVO>> friendList(@RequestParam String id) {
        FriendDAO friendDAO = new FriendDAO();
        TreeSet<FriendVO> set = friendDAO.friendList(id);
        return ResponseEntity.ok(set);
    }
}
