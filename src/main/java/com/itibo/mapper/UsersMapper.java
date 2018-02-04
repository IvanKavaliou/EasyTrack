package com.itibo.mapper;

import com.itibo.database.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {
    void upEmail(UserInfo userInfo);
    UserInfo getUserByGoogleId(String googleid);
    String getUserEmailById(Integer id);
    Integer getUserIdByEmail(String email);
    Integer getUserIdByGoogleId(String googleid);
    void addUser(@Param("email") String email, @Param("googleid") String googleid);
}
