package model2_study.com.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model2_study.com.dao.UserDao;
import model2_study.com.dao.UserDaoImp;
import model2_study.com.dto.UserDto;
@WebServlet("/login.do")
public class UserLoginServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Controller : 요청을 처리하는 곳 
		String user_id=req.getParameter("user_id");
		String pw=req.getParameter("pw");
		System.out.println(user_id+"/"+pw);
		//Modle :db 서비스를 제공하는 객체
		UserDao userDao=new UserDaoImp();
		UserDto user=null;
		try {
			user=userDao.login(user_id, pw);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println(user);
		HttpSession session=req.getSession();
		if(user!=null) {
			session.setAttribute("loginUser", user);
			resp.sendRedirect("./");
		}else {
			resp.sendRedirect("./loginForm.jsp");
		}
	
	}
}
