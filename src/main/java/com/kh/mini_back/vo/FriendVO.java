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
public class FriendVO implements Comparable<FriendVO> {
    private String sendId;
    private String sendNick;
    private String sendProfile;
    private String receiveId;
    private String receiveNick;
    private String receiveProfile;
    private String accept;

    @Override
    public int compareTo(FriendVO o) {
        if (this.sendId.equals(o.sendId)) return 0;
        else return 1;
    }
}
