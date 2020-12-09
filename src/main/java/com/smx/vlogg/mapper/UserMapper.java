package com.smx.vlogg.mapper;

import com.smx.vlogg.model.BindPhoneVo;
import com.smx.vlogg.model.entity.Feedback;
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
    @Insert("INSERT INTO t_user (wx_openid,phone,nickname,avatar,create_time) VALUES (#{wxOpenId},#{phone},#{nickname},#{avatar},#{createTime})")
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


    /**
     * 更新用户信息
     * @param user
     * @throws SQLException
     */
    @Update("UPDATE t_user SET password=#{password},nickname=#{nickname}," +
            "avatar=#{avatar},gender=#{gender},birthday=#{birthday},address=#{address} " +
            "WHERE phone=#{phone}")
    void updateUser(User user) throws SQLException;


    /**
     * 更新用户密码
     * @param user
     * @throws SQLException
     */
    @Update("UPDATE t_user SET password=#{password} WHERE phone=#{phone}")
    void updatePassword(User user) throws SQLException;

    /**
     * 添加用户反馈信息
     * @param feedback
     * @throws SQLException
     */
    @Insert("INSERT INTO t_feedback (title,content,phone) VALUES (#{title},#{content},#{phone}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertFeedback(Feedback feedback) throws SQLException;

    /**
     * 根据微信openId查询用户信息
     * @param wxOpenId
     * @return
     * @throws SQLException
     */
    @Select({"<script>" +
            "SELECT * FROM t_user " +
            "WHERE 1=1 " +
            "<when test='wxOpenId!=null'> " +
            "AND wx_openid = #{wxOpenId} " +
            "</when> " +
            "</script>"})
    User findUserByOpenId(@Param("wxOpenId") String wxOpenId) throws SQLException;


    /**
     * 给指定openId用户添加手机号
     * @param
     * @throws SQLException
     */
    @Update("UPDATE t_user SET phone=#{phone} WHERE wx_openid=#{wxOpenId} ")
    void updatePhoneByOpenId(@Param("phone") String phone,@Param("wxOpenId") String wxOpenId) throws SQLException;

}
