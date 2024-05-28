package com.kh.mini_back.dao;






import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.*;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.TreeSet;

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
    // 모임생성자 모임리스트에 등록
    public Boolean master(MeetingVO meetingVO) {
        String query = null;
        int ret = 0;
        int no = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = Common.getConnection();

            query = "SELECT MEETING_NO FROM MEETING_TB WHERE USER_ID = ? AND MEETING_DETAILS = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, meetingVO.getId());
            pstmt.setString(2, meetingVO.getDetail());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                no = rs.getInt("MEETING_NO");
            }

            Common.close(rs);
            Common.close(pstmt);

            query = "INSERT INTO MEETING_MEMBER_TB(MEETING_NO,USER_ID,MASTER,ACCEPT) VALUES (?, ?, 'TRUE','TRUE')";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, no);
            pstmt.setString(2, meetingVO.getId());
            ret = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }

        return ret > 0;
    }
    // 신청하기
    public boolean application(MeetingVO meetingVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "INSERT INTO MEETING_MEMBER_TB( MEETING_NO,USER_ID, ACCEPT, DETAIL ) VALUES (?,?,'FALSE',?)";

            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, meetingVO.getNo());
            pstmt.setString(2, meetingVO.getId());
            pstmt.setString(3, meetingVO.getDetail());

            ret = pstmt.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        Common.close(stmt);
        Common.close(conn);

        return ret > 0;
    }
    //모임수락
    public Boolean acceptOk(MeetingMemberVO meetingMemberVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "UPDATE MEETING_MEMBER_TB SET ACCEPT = 'TRUE' WHERE MEETING_NO = ? AND USER_ID = ?";

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, meetingMemberVO.getNo());
            pstmt.setString(2, meetingMemberVO.getId());
            ret = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }
        return ret>0;
    }
    //친구삭제
    public Boolean delMember(MeetingMemberVO meetingMemberVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "DELETE FROM MEETING_MEMBER_TB WHERE WHERE MEETING_NO = ? AND USER_ID = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, meetingMemberVO.getNo());
            pstmt.setString(2, meetingMemberVO.getId());
            ret = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }
        return ret>0;
    }
    // 모임 신청중인지 확인
    public List<Integer> conAccept(String id) {
        List<Integer> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String query = "SELECT MEETING_NO FROM MEETING_MEMBER_TB WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()){
             int num = rs.getInt("MEETING_NO");
             list.add(num);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }
        return list;
    }
    //모임정보
    public List<MeetingVO> meetingInfo(int mno) {
        List<MeetingVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String query = "SELECT * FROM MEETING_TB WHERE MEETING_NO = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, mno);
            rs = pstmt.executeQuery();

            while (rs.next()){
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

        } finally {
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }
        return list;
    }
    public List<MeetingVO> myMeetingList(String myId) {
        List<MeetingVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();

            String query = "SELECT * FROM MEETING_TB WHERE MEETING_NO IN(SELECT MEETING_NO FROM MEETING_MEMBER_TB WHERE USER_ID = ? AND ACCEPT = 'TRUE')";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, myId);
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
    // 모임 신청자 list
    public List<MeetingMemberVO> acceptList(String id) {
        List<MeetingMemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();

            String query = "SELECT * FROM MEETING_MEMBER_TB WHERE MEETING_NO in (SELECT MEETING_NO  FROM MEETING_TB WHERE USER_ID  = ?) AND ACCEPT = 'FALSE'";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int no = rs.getInt("MEETING_NO");
                String userId = rs.getString("USER_ID");
                String master = rs.getString("MASTER");
                String accept = rs.getString("ACCEPT");
                String detail = rs.getString("DETAIL");
                MeetingMemberVO meetingMemberVO = new MeetingMemberVO(no,userId,master,accept,detail);
                list.add(meetingMemberVO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
     finally {
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
    }
        return list;
    }
    //참여자 목록
    public List<MeetingMemberVO> memberList(int meetingNo) {
        List<MeetingMemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();

            String query = "SELECT * FROM MEETING_MEMBER_TB WHERE MEETING_NO = ? AND ACCEPT = 'TRUE'";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, meetingNo);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                MeetingMemberVO meetingMemberVO = new MeetingMemberVO();
                meetingMemberVO.setNo(rs.getInt("MEETING_NO"));
                meetingMemberVO.setId(rs.getString("USER_ID"));

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
                Timestamp bdate= rs.getTimestamp("BOARD_DATE");
                Date sdate= rs.getDate("SCHEDULE_DATE");
                ScheduleVO scheduleVO = new ScheduleVO(mno,sno,title,contents,id,bdate,sdate);
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
            String query = "SELECT USER_ID,SCHEDULE_DATE " +
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
    public List<ChatVO> chatList(int no) {
        List<ChatVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT * FROM CHAT_TB WHERE MEETING_NO = '" + no + "'";
            rs = stmt.executeQuery(query);

            while(rs.next()) {
                ChatVO chatVO = new ChatVO();
                chatVO.setMeetingNo(rs.getInt("MEETING_NO"));
                chatVO.setId(rs.getString("USER_ID"));
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
            String query = "INSERT INTO CHAT_TB VALUES (?,?,?,SYSDATE)";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatVO.getMeetingNo());
            pstmt.setString(2, chatVO.getId());
            pstmt.setString(3, chatVO.getContents());
            ret = pstmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Common.close(pstmt);
        Common.close(conn);

        return ret > 0;
    }
    public Boolean delSchedule(int sno) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "DELETE FROM SCHEDULE_TB WHERE SCHEDULE_NO = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, sno);
            ret = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return ret>0;
    }
    public Boolean send(ScheduleVO scheduleVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "INSERT INTO SCHEDULE_TB VALUES (?,SCHEDULE_SEQ.NEXTVAL,?,?,?,SYSDATE,?)";

            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, scheduleVO.getMno());
            pstmt.setString(2, scheduleVO.getTitle());
            pstmt.setString(3, scheduleVO.getContents());
            pstmt.setString(4, scheduleVO.getId());
            pstmt.setDate(5, scheduleVO.getSdate());

            ret = pstmt.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        Common.close(stmt);
        Common.close(conn);
        return ret > 0;
    }
    public List<MeetingVO> mainsel(String searchType, String keyword) {
        List<MeetingVO> list = new ArrayList<>();
        String sql = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = Common.getConnection();
            if (searchType.equals("제목")) {
                sql = "SELECT * FROM MEETING_TB WHERE MEETING_TITLE LIKE ?";
            } else if (searchType.equals("작성자")) {
                sql = "SELECT * FROM MEETING_TB WHERE USER_ID = ?";
            }
            pStmt = conn.prepareStatement(sql);
            if (searchType.equals("제목")) {
                pStmt.setString(1, "%" + keyword + "%");
            } else if (searchType.equals("작성자")) {
                pStmt.setString(1, keyword);
            }
            rs = pStmt.executeQuery();
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
        } finally {
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
