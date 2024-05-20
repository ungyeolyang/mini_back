package com.kh.mini_back.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterVO {
    private int no;
    private String sender;
    private String receiver;
    private String title;
    private String contents;
    private Date date;
    private String view;
}
