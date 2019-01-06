package com.yihaomen.test;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yihaomen.mybatis.inter.IUserOperation;
import com.yihaomen.mybatis.model.User;

public class Test {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	static {
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	static void forString() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			User user = (User) session.selectOne("com.yihaomen.mybatis.model.UserMapper.selectUserByID", 1);
			System.out.println("forString()=="+user.getUserAddress());
			System.out.println("forString()=="+user.getUserName());
		} finally {
			session.close();
		}
	}

	static void forClass() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IUserOperation userOperation = session.getMapper(IUserOperation.class);
			User user = userOperation.selectUserByID(1);
			System.out.println("forClass()=="+user.getUserAddress());
			System.out.println("forClass()=="+user.getUserName());
		} finally {
			session.close();
		}
	}

	public static void main(String[] args) {
		forString();//类全路径调用 
		forClass();//类名调用 
	}
}