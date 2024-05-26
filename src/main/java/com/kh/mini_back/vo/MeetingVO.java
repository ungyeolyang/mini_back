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
public class MeetingVO {
   private int no;
   private String title;
   private String name;
   private String location;
   private Date duration1;
   private Date duration2;
   private int personnel;
   private String id;
   private String category;
   private String detail;
}
