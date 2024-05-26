package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MeetingDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    // 모집하기
    public boolean recruit(MeetingVO meetingVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "INSERT INTO MEETING_TB VALUES (MEETING_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?)";

            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, meetingVO.getTitle());
            pstmt.setString(2, meetingVO.getName());
            pstmt.setString(3, meetingVO.getLocation());
            pstmt.setDate(4, meetingVO.getDuration1());
            pstmt.setDate(5, meetingVO.getDuration2());
            pstmt.setInt(6, meetingVO.getPersonnel());
            pstmt.setString(7, meetingVO.getId());
            pstmt.setString(8, meetingVO.getCategory());
            pstmt.setString(9, meetingVO.getDetail());

            ret = pstmt.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        Common.close(stmt);
        Common.close(conn);

        return ret > 0;
    }

    public List<MeetingVO> meetingList() {
        List<MeetingVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();

            String query = "SELECT * FROM MEETING_TB";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int no = rs.getInt("MEETING_NO");
                String title = rs.getString("MEETING_TITLE");
                String name = rs.getString("MEETING_NAME");
                String location = rs.getString("MEETING_LOCATION");
                Date duration1 = rs.getDate("MEETING_DURATION");
                Date duration2 = rs.getDate("MEETING_DURATION2");
                int personnel = rs.getInt("MEETING_PERSONNEL");
                String id = rs.getString("USER_ID");
                String category = rs.getString("MEETING_CATEGORY");
                String detail = rs.getString("MEETING_DETAILS");
                MeetingVO meetingVO = new MeetingVO(no,title,name,location,duration1,duration2,personnel,id,category,detail);
                list.add(meetingVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return list;
    }
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
                Date sdate= rs.getDate("SCHEDULE_DATE");
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
    public TreeSet<ScheduleVO> writerList(int mno, int year, int month) {
        TreeSet<ScheduleVO> set = new TreeSet<>();
        try {
            conn = Common.getConnection();
            String query = "SELECT USER_ID,USER_NICK,SCHEDULE_DATE " +
                    "FROM SCHEDULE_TB " +
                    "WHERE MEETING_NO = ? " +
                    "  AND EXTRACT(YEAR FROM SCHEDULE_DATE) = ? " +
                    "  AND EXTRACT(MONTH FROM SCHEDULE_DATE) = ? ";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, mno);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                ScheduleVO scheduleVO = new ScheduleVO();
                scheduleVO.setId(rs.getString("USER_ID"));
                scheduleVO.setNick(rs.getString("USER_NICK"));
                scheduleVO.setSdate(rs.getDate("SCHEDULE_DATE"));
                set.add(scheduleVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return set;
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
