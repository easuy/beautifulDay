package com.yihaomen.mybatis.inter;
import com.yihaomen.mybatis.model.User;
import com.yihaomen.mybatis.model.UserMapper2;

public interface IUserOperation {
	User selectUserByID(Integer id);
}