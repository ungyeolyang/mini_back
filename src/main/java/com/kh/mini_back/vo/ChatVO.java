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
public class ChatVO {
    private int meetingNo;
    private String id;
    private String nick;
    private String contents;
    private Timestamp date;
}
