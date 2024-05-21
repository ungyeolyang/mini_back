package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.UserInfoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public Boolean editInfo(UserInfoVO userInfoVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "UPDATE USER_INFO_TB SET USER_ID = ?, USER_PW = ?, USER_BIRTH = ?," +
                    " USER_NICK=?,USER_EMAIL=?,USER_GENDER=?,USER_INTRODUTION =? WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userInfoVO.getId());
            pstmt.setString(2, userInfoVO.getPw());
            pstmt.setDate(3, userInfoVO.getBirth());
            pstmt.setString(4, userInfoVO.getNick());
            pstmt.setString(5, userInfoVO.getEmail());
            pstmt.setString(6, userInfoVO.getGender());
            pstmt.setString(7, userInfoVO.getIntrodution());
            pstmt.setString(8, userInfoVO.getId());
            ret = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            Common.close(pstmt);
            Common.close(conn);
        }

        return ret>0;
    }

    public Boolean editProfile(UserInfoVO userInfoVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "UPDATE USER_INFO_TB SET USER_PROFILE =? WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userInfoVO.getProfile());
            pstmt.setString(2, userInfoVO.getId());
            ret = pstmt.executeUpdate();
            System.out.println(ret);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            Common.close(pstmt);
            Common.close(conn);
        }

        return ret>0;
    }
    //아이디를 받아 정보출력
    public List<UserInfoVO> getInfo(String id) {
        List<UserInfoVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String query = "SELECT * FROM USER_INFO_TB WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String pw = rs.getString("USER_PW");
                Date birth = rs.getDate("USER_BIRTH");
                String nick = rs.getString("USER_NICK");
                String email = rs.getString("USER_EMAIL");
                String gender = rs.getString("USER_GENDER");
                String intro = rs.getString("USER_INTRODUTION");
                String profile = rs.getString("USER_PROFILE");
                UserInfoVO userInfoVO = new UserInfoVO(id,pw,birth,nick,email,gender,intro,profile);
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
    //아이디에 맞는 비밀번호이면 true
    public boolean checkPw(UserInfoVO userInfoVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "SELECT USER_ID FROM USER_INFO_TB WHERE USER_ID = ? AND USER_PW = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1,userInfoVO.getId());
            pstmt.setString(2, userInfoVO.getPw());

            ret = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);

        return ret > 0;
    }
}
