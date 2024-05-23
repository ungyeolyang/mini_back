package com.kh.mini_back.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CommentVo {
    private int comment_no;
    private String comment_detail;
    private int board_no;
    private String comment_id;
    private Date comment_date;
}
