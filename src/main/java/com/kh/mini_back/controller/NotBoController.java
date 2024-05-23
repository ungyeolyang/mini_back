package com.kh.mini_back.controller;



import com.kh.mini.dao.NotBoDao;
import com.kh.mini.vo.CommentVo;
import com.kh.mini.vo.NotBoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/dooin")
@Slf4j
public class NotBoController {

    // 게시판에 글쓰기
    @PostMapping("/notboinsert")
    public ResponseEntity<Boolean> NotBoinsert(@RequestBody NotBoVo vo){
        NotBoDao dao=new NotBoDao();
        System.out.println(vo.getBoard_title());
        System.out.println(vo.getBoard_category());
        System.out.println(vo.getBoard_de());
        System.out.println(vo.getUser_id());
        System.out.println(vo.getImageurl());
        boolean isTrue = dao.notboinsert(vo);
        return ResponseEntity.ok(isTrue);
    }
    @GetMapping("/boardsl")
    public ResponseEntity<List<NotBoVo>> boardList(@RequestParam String board_category){
        log.debug("카테고리로 조회 : " + board_category);
        NotBoDao dao=new NotBoDao();
        List<NotBoVo> list =dao.boardSel(board_category);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/myboardsl")
    public ResponseEntity<List<NotBoVo>> myboardList(@RequestParam String user_id){
        log.debug("유저 id로 조회 : " + user_id);
        NotBoDao dao=new NotBoDao();
        List<NotBoVo> list =dao.myboardSel(user_id);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/sersel")
    public ResponseEntity<List<NotBoVo>> serSel(@RequestParam String searchType, @RequestParam String keyword) {
        NotBoDao dao = new NotBoDao();
        List<NotBoVo> list = dao.sersel(searchType, keyword);
        System.out.println(searchType);
        System.out.println(keyword);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/detail")
    public ResponseEntity<List<NotBoVo>> detail(@RequestParam String board_no){
        log.debug("번호로 조회 : " + board_no);
        NotBoDao dao = new NotBoDao();
        List<NotBoVo> list = dao.detail(board_no);
        return ResponseEntity.ok(list);
    }
    @DeleteMapping("/delete/{board_no}")
    public ResponseEntity<Boolean> detaildel(@PathVariable int board_no) {
        System.out.println("aaa: " + board_no); // 디버깅용 로그
        NotBoDao dao = new NotBoDao();
        boolean isTrue = dao.detailDelete(board_no);
        return ResponseEntity.ok(isTrue);
    }
    @PostMapping("/notboupdate")
    public ResponseEntity<Boolean> NotBoUpdate(@RequestBody NotBoVo vo){
        NotBoDao dao=new NotBoDao();
        System.out.println("제목"+vo.getBoard_title());
        System.out.println("내용"+vo.getBoard_de());
        System.out.println("이미지"+vo.getImageurl());
        System.out.println("번호"+vo.getBoard_no());
        boolean isTrue = dao.notboUpdate(vo);
        return ResponseEntity.ok(isTrue);
    }
    @PostMapping("/upView")
    public ResponseEntity<Boolean> upView(@RequestBody NotBoVo vo){
        NotBoDao dao=new NotBoDao();
        System.out.println("번호"+vo.getBoard_no());
        boolean isTrue = dao.upView(vo);
        return ResponseEntity.ok(isTrue);
    }
    @PostMapping("/commentInsert")
    public ResponseEntity<Boolean> commentInsert(@RequestBody CommentVo vo){
        NotBoDao dao=new NotBoDao();
        System.out.println(vo.getComment_detail());
        System.out.println(vo.getBoard_no());
        System.out.println(vo.getComment_id());
        boolean isTrue = dao.commentInsert(vo);
        return ResponseEntity.ok(isTrue);
    }
    @GetMapping("/commentSel")
    public ResponseEntity<List<CommentVo>> commentSel(@RequestParam String board_no){
        log.debug("댓글을 조회 : " + board_no);
        NotBoDao dao=new NotBoDao();
        List<CommentVo> list =dao.commentSel(board_no);
        return ResponseEntity.ok(list);
    }
    @DeleteMapping("/commentdel/{comment_no}")
    public ResponseEntity<Boolean> commentdel(@PathVariable String comment_no) {
        System.out.println("삭제번호: " + comment_no); // 디버깅용 로그
        NotBoDao dao = new NotBoDao();
        boolean isTrue = dao.commentDelete(Integer.parseInt(comment_no));
        return ResponseEntity.ok(isTrue);
    }
}

