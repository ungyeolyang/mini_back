package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.FriendVO;
import com.kh.mini_back.vo.LetterVO;
import com.kh.mini_back.vo.UserInfoVO;

import java.sql.*;
import java.util.*;

public class FriendDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    public Boolean addFriend(FriendVO friendVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "INSERT INTO FRIEND_TB VALUES (?,?,?,?,?,?,'FALSE')";

            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, friendVO.getSendId());
            pstmt.setString(2, friendVO.getSendNick());
            pstmt.setString(3, friendVO.getSendProfile());
            pstmt.setString(4, friendVO.getReceiveId());
            pstmt.setString(5, friendVO.getReceiveNick());
            pstmt.setString(6, friendVO.getReceiveProfile());
            ret = pstmt.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        Common.close(stmt);
        Common.close(conn);
        return ret > 0;
    }
    //친구신청중이면 true 반환
    public Boolean conSend(FriendVO friendVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "SELECT * FROM FRIEND_TB WHERE FRIEND_SEND_ID = ? AND FRIEND_RECEIVE_ID = ? AND FRIEND_ACCEPT = 'FALSE'";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, friendVO.getSendId());
            pstmt.setString(2, friendVO.getReceiveId());
            ret += pstmt.executeUpdate();

            pstmt.setString(1, friendVO.getReceiveId());
            pstmt.setString(2, friendVO.getSendId());
            ret += pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            Common.close(pstmt);
            Common.close(conn);
        }
        return ret>0;
    }

    public Boolean conFriend(FriendVO friendVO) {
        int ret = 0;
        String query = null;
        try {
            conn = Common.getConnection();
            query = "SELECT * FROM FRIEND_TB WHERE FRIEND_SEND_ID = ? AND FRIEND_RECEIVE_ID = ? AND FRIEND_ACCEPT = 'TRUE'";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, friendVO.getSendId());
            pstmt.setString(2, friendVO.getReceiveId());
            ret += pstmt.executeUpdate();

            pstmt.setString(1, friendVO.getReceiveId());
            pstmt.setString(2, friendVO.getSendId());
            ret += pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            Common.close(pstmt);
            Common.close(conn);
        }
        return ret>0;
    }

    //친구신청을 보낸사람들 출력
    public List<FriendVO> sendList (String id) {
        List<FriendVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String query = "SELECT * FROM FRIEND_TB WHERE FRIEND_RECEIVE_ID = ? AND FRIEND_ACCEPT = 'FALSE'";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FriendVO friendVO = new FriendVO();
                friendVO.setSendId(rs.getString("FRIEND_SEND_ID"));
                friendVO.setSendNick(rs.getString("FRIEND_SEND_NICK"));
                friendVO.setSendProfile(rs.getString("FRIEND_SEND_PROFILE"));
                list.add(friendVO);
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
    //친구수락
    public Boolean friendOk(FriendVO friendVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "UPDATE FRIEND_TB SET FRIEND_ACCEPT = 'TRUE' WHERE FRIEND_SEND_ID = ? AND FRIEND_RECEIVE_ID = ?";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, friendVO.getSendId());
            pstmt.setString(2, friendVO.getReceiveId());
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
    public Boolean delFriend(FriendVO friendVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "DELETE FROM FRIEND_TB WHERE FRIEND_SEND_ID = ? AND FRIEND_RECEIVE_ID = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, friendVO.getSendId());
            pstmt.setString(2, friendVO.getReceiveId());
            ret += pstmt.executeUpdate();

            pstmt.setString(1, friendVO.getReceiveId());
            pstmt.setString(2, friendVO.getSendId());
            ret += pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }
        return ret>0;
    }
    //내 친구목록 출력
    public TreeSet<FriendVO> friendList (String id) {
        String query = null;
        TreeSet<FriendVO> set = new TreeSet<>();
        try {
            conn = Common.getConnection();
            query = "SELECT FRIEND_SEND_ID, FRIEND_SEND_NICK, FRIEND_SEND_PROFILE FROM FRIEND_TB WHERE FRIEND_RECEIVE_ID = ?" +
                    " AND FRIEND_ACCEPT = 'TRUE'";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FriendVO friendVO = new FriendVO();
                friendVO.setSendId(rs.getString("FRIEND_SEND_ID"));
                friendVO.setSendNick(rs.getString("FRIEND_SEND_NICK"));
                friendVO.setSendProfile(rs.getString("FRIEND_SEND_PROFILE"));
                set.add(friendVO);
            }

            conn = Common.getConnection();
            query = "SELECT FRIEND_RECEIVE_ID, FRIEND_RECEIVE_NICK, FRIEND_RECEIVE_PROFILE FROM FRIEND_TB WHERE FRIEND_SEND_ID = ? " +
                    "AND FRIEND_ACCEPT = 'TRUE'";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FriendVO friendVO = new FriendVO();
                friendVO.setSendId(rs.getString("FRIEND_RECEIVE_ID"));
                friendVO.setSendNick(rs.getString("FRIEND_RECEIVE_NICK"));
                friendVO.setSendProfile(rs.getString("FRIEND_RECEIVE_PROFILE"));
                set.add(friendVO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }
        return set;
    }
}
