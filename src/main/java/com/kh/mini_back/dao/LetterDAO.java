package com.kh.mini_back.dao;

import com.kh.mini_back.utils.Common;
import com.kh.mini_back.vo.UserInfoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LetterDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

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
}
