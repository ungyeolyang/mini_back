package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.UserInfoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
}
