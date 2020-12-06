package com.smx.vlogg.mapper;

import com.smx.vlogg.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;

/**
 * @ClassName UserMapper
 * @Description 用户Mapper
 * @Author moses
 * @Date 2020/12/5
 **/
public interface UserMapper {
    /**
     * user 入参user对象
     * @param user
     * @throws SQLException
     */
    @Insert("INSERT INTO t_user (phone,nickname,avatar,create_time) VALUES (#{phone},#{nickname},#{avatar},#{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(User user) throws SQLException;

    /**
     * 根据手机号查询用户信息
     * @param phone 手机号
     * @return  user 用户对象
     * @throws SQLException
     */
    @Select({"<script>",
            "select * from t_user ",
            "where 1=1",
            "<when test='phone!=null'> ",
            "and phone=#{phone}",
            "</when> ",
            "</script>"})
    User findUserByPhone(@Param("phone") String phone) throws SQLException;


    @Update("UPDATE t_user SET password=#{password},nickname=#{nickname}," +
            "avatar=#{avatar},gender=#{gender},birthday=#{birthday},address=#{address} " +
            "WHERE phone=#{phone}")
    void updateUser(User user) throws SQLException;




}
