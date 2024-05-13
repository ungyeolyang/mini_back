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
public class MemberVO {
    private String id;
    private String pw;
    private String nick;
    private String email;
    private String introdution;
}
