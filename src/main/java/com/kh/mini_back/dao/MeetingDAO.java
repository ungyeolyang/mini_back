package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.ChatVO;
import com.kh.mini_back.vo.MeetingMemberVO;
import com.kh.mini_back.vo.ScheduleVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    //참여자 목록
    public List<MeetingMemberVO> memberList(int meetingNo) {
        List<MeetingMemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();

            String query = "SELECT * FROM MEETING_MEMBER_TB WHERE MEETING_NO = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, meetingNo);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int no = rs.getInt("MEETING_NO");
                String id = rs.getString("USER_ID");
                String nick= rs.getString("USER_NICK");
                String profile= rs.getString("USER_PROFILE");
                MeetingMemberVO meetingMemberVO = new MeetingMemberVO(no,id,nick,profile);
                list.add(meetingMemberVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return list;
    }
    //공지사항 목록
    public List<ScheduleVO> scheduleList(int meetingNo) {
        List<ScheduleVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();

            String query = "SELECT * FROM SCHEDULE_TB WHERE MEETING_NO = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, meetingNo);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int mno = rs.getInt("MEETING_NO");
                int sno = rs.getInt("SCHEDULE_NO");
                String title = rs.getString("SCHEDULE_TITLE");
                String contents = rs.getString("SCHEDULE_CONTENTS");
                String id= rs.getString("USER_ID");
                String nick= rs.getString("USER_NICK");
                Timestamp bdate= rs.getTimestamp("BOARD_DATE");
                Timestamp sdate= rs.getTimestamp("SCHEDULE_DATE");
                ScheduleVO scheduleVO = new ScheduleVO(mno,sno,title,contents,id,nick,bdate,sdate);
                list.add(scheduleVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return list;
    }
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
                chatVO.setContents(rs.getString("CHAT_CONTENTS"));
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
