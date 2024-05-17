package com.kh.mini.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class NotBoVo {
    private int board_no;
    private String board_title;
    private String board_category;
    private String board_de;
    private String user_id;
    private Date board_date;
    private int board_view;
}
