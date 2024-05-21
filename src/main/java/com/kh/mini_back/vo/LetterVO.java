package com.kh.mini_back.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterVO {
    private int no;
    private String sender;
    private String senderNick;
    private String receiver;
    private String receiverNick;
    private String title;
    private String contents;
    private Timestamp date;
    private String view;
}
