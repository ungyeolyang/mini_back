package com.kh.mini.controller;



import com.kh.mini.dao.NotBoDao;
import com.kh.mini.vo.NotBoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}

