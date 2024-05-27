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
public class ScheduleVO implements Comparable<ScheduleVO>{
    private int mno;
    private int sno;
    private String title;
    private String contents;
    private String id;
    private Timestamp bdate;
    private Date sdate;

    @Override
    public int compareTo(ScheduleVO o) {
        if(this.id.equals(o.id) && this.sdate.equals(o.sdate)) return 0;
        else return 1;
    }
}
