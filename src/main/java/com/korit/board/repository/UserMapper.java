package com.korit.board.repository;

import com.korit.board.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public Integer saveUser(User user);
    public Integer checkDuplicate(User user);
    public User findUserByEmail(String email);
    public User findUserByOauth2Id(String oauth2Id);
    public int updateEnabledToEmail(String email);
    public int updateProfileUrl(User user);
    public int updatePassword(User user);
}
