package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.ChatVO;
import com.kh.mini_back.vo.UserInfoVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MeetingDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public List<ChatVO> chatList(int meetingNo) {
        List<ChatVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT * FROM CHAT_TB WHERE MEETING_NO = '" + meetingNo + "'";
            rs = stmt.executeQuery(query);

            while(rs.next()) {
                ChatVO chatVO = new ChatVO();
                chatVO.setMeetingNo(rs.getInt("MEETING_NO"));
                chatVO.setId(rs.getString("USER_ID")); ;
                chatVO.setNick(rs.getString("USER_NICK"));
                chatVO.setContents(rs.getString("CHAT_DE"));
                chatVO.setDate(rs.getTimestamp("CHAT_DATE"));
                list.add(chatVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return list;
    }

    public boolean chat(ChatVO chatVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "INSERT INTO CHAT_TB VALUES (1,'" + chatVO.getId() + "','" +chatVO.getNick() + "','" + chatVO.getContents() + "',SYSDATE)";

            ret = stmt.executeUpdate(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Common.close(stmt);
        Common.close(conn);

        return ret > 0;
    }

}
