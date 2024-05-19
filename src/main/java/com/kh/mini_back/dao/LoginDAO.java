package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.UserInfoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    // 아이디와 비밀번호를 입력받아 일치하면 true 반환
    public boolean logIn(String id,String pw) {
        boolean isLogin = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_ID,USER_PW FROM USER_INFO_TB WHERE USER_ID = '" + id +"' AND USER_PW = '" + pw + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                isLogin = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isLogin;
    }
    //아이디를 입력받아 이미존재하면 true 반환
    public boolean checkId(String id) {
        boolean isId = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_ID FROM USER_INFO_TB WHERE USER_ID = '" + id + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                isId = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isId;
    }
    //이메일을 입력받아 이미존재하면 true 아니면 false 출력,
    public boolean checkEmail(String email) {
        boolean isEmail = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_EMAIL FROM USER_INFO_TB WHERE USER_EMAIL = '" + email + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("USER_EMAIL"));
                isEmail = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isEmail;
    }

    //이메일을 입력받아 아이디 비밀번호 찾기
    public String findInfo(String email, String category) {
        String id = null;
        String pw = null;
        String rst = null;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String query = "SELECT USER_ID, USER_PW FROM USER_INFO_TB WHERE USER_EMAIL = '" + email + "'";
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                id = rs.getString("USER_ID");
                pw = rs.getString("USER_PW");
                if(category.contains("아이디")) rst = id;
                else rst = pw;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return rst;
    }
    //회원가입 성공하면 true 반환
    public boolean SignUp(UserInfoVO userInfoVO) {
        int ret = 0;
        try {
            conn = Common.getConnection();
            String query = "INSERT INTO USER_INFO_TB VALUES (?,?,?,?,?,?,?,?)";

            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, userInfoVO.getId());
            pstmt.setString(2, userInfoVO.getPw());
            pstmt.setDate(3, userInfoVO.getBirth());
            pstmt.setString(4, userInfoVO.getNick());
            pstmt.setString(5, userInfoVO.getEmail());
            pstmt.setString(6, userInfoVO.getGender());
            pstmt.setString(7, "안녕하세요 " + userInfoVO.getNick() +"입니다.");
            pstmt.setString(8, userInfoVO.getProfile());

            ret = pstmt.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        Common.close(stmt);
        Common.close(conn);

       return ret > 0;
    }
}
