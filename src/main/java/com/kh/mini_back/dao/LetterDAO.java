package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.FriendVO;
import com.kh.mini_back.vo.LetterVO;
import com.kh.mini_back.vo.UserInfoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LetterDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    //아이디를 입력받아 닉네임 반환
    public String getNick(String id) {
        String nick = null;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_NICK FROM USER_INFO_TB WHERE USER_ID = '" + id + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
             nick = rs.getString("USER_NICK");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return nick;
    }
    //아이디를 받아 정보출력
    public List<UserInfoVO> getId(String id) {
        List<UserInfoVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String query = "SELECT USER_ID,USER_NICK FROM USER_INFO_TB WHERE USER_ID Like ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%"+id+"%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserInfoVO userInfoVO = new UserInfoVO();
                String userId = rs.getString("USER_ID");
                String nick = rs.getString("USER_NICK");
                userInfoVO.setId(userId);
                userInfoVO.setNick(nick);
                list.add(userInfoVO);
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
    public Boolean send(LetterVO letterVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "INSERT INTO LETTER_TB VALUES (LETTER_SEQ.NEXTVAL,?,?,?,?,?,?,SYSDATE,'FALSE')";

            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, letterVO.getSender());
            pstmt.setString(2, letterVO.getSenderNick());
            pstmt.setString(3, letterVO.getReceiver());
            pstmt.setString(4, letterVO.getReceiverNick());
            pstmt.setString(5, letterVO.getTitle());
            pstmt.setString(6, letterVO.getContents());

            ret = pstmt.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        Common.close(stmt);
        Common.close(conn);
        return ret > 0;
    }
    //친구삭제
    public Boolean delLetter(int sno) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "DELETE FROM LETTER_TB WHERE LETTER_NO = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, sno);
            ret = pstmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }
        return ret>0;
    }

    public List<LetterVO> getLetter(String id, String category) {
        List<LetterVO> list = new ArrayList<>();
        String query = null;
        try {
            conn = Common.getConnection();
            if(category.equals("send")) {
                query = "SELECT * FROM LETTER_TB WHERE LETTER_SENDER = ?";
            }
            else  {query = "SELECT * FROM LETTER_TB WHERE LETTER_RECEIVER = ?";}

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int letterNo = rs.getInt("LETTER_NO");
                String sender = rs.getString("LETTER_SENDER");
                String senderNick = rs.getString("LETTER_SENDERNICK");
                String receiver = rs.getString("LETTER_RECEIVER");
                String receiverNCIK = rs.getString("LETTER_RECEIVERNICK");
                String title = rs.getString("LETTER_TITLE");
                String contents = rs.getString("LETTER_CONTENTS");
                Timestamp date = rs.getTimestamp("LETTER_DATE");
                String view = rs.getString("LETTER_VIEW");
                LetterVO letterVO = new LetterVO(letterNo,sender,senderNick,receiver,receiverNCIK,title,contents,date,view);
                list.add(letterVO);
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
    public Boolean setView(String no) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "UPDATE LETTER_TB SET LETTER_VIEW = 'TRUE' WHERE LETTER_NO = ?";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, no);
            ret = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }
        return ret>0;
    }

}
