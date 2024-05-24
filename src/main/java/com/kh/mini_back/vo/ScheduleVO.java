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
public class ScheduleVO {
    int mno;
    int sno;
    String title;
    String contents;
    String id;
    String nick;
    Timestamp bdate;
    Timestamp sdate;
}
