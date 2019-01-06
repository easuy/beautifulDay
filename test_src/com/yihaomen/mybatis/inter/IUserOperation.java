package com.yihaomen.mybatis.inter;
import java.util.List;

import com.yihaomen.mybatis.model.Article;
import com.yihaomen.mybatis.model.User;

public interface IUserOperation {
	User selectUserByID(Integer id);
	public List<User> selectUsers(String userName);   
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(int id);
	public List<Article> getUserArticles(int id);
}