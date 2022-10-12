package model2_study.com.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dto.BoardDto;
import model2_study.com.dto.UserDto;
@WebServlet("/boardInsert.do")
public class BoardInsertServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//입력 폼 (로그인 한 사람만 해당 폼에 이동 가능,로그인 하지 않은 사람은 list로 이동)
		//세션 객체도 파라미터에 포함된 상태
		String forwardPage="./boardInsertForm.jsp"; //저장 실패시 이동하는 페이지
		String redirectPage="./login.do";
		HttpSession session=req.getSession();
		Object loginUser_obj=session.getAttribute("loginUser");
		
		if(loginUser_obj==null) {
			resp.sendRedirect(redirectPage);
			session.setAttribute("msg", "게시글 등록은 로그인 후 가능 합니다.");
		}else {
			req.getRequestDispatcher(forwardPage).forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.저장 파라미터 처리, 2.저장 호출 ,3.결과에 따라 응답처리
		req.setCharacterEncoding("UTF-8");
		Object loginUser_obj=req.getSession().getAttribute("loginUser");
		
		String title=req.getParameter("title");
		String userId=req.getParameter("user_id");
		String contents=req.getParameter("contents");
		int insert=0;
		BoardDao boardDao=null;
		String errorPage="./boardInsertForm.jsp"; //저장 실패시 이동하는 페이지
		String redirectPage="./boardList.do";//저장 성공시 이동하는 페이지
		
		try {
			UserDto loginUser=(UserDto)loginUser_obj; //null이면 오류
			if(loginUser.getUserId().equals(userId)) {
				boardDao=new BoardDaoImp();
				BoardDto boardDto=new BoardDto();
				boardDto.setTitle(title);
				boardDto.setUser_id(userId);
				boardDto.setContents(contents);
				insert=boardDao.insert(boardDto);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(boardDao!=null)boardDao.close();
		}
		if(insert>0) {
			resp.sendRedirect(redirectPage);
		}else {
			resp.sendRedirect(errorPage);
		}
	}
}



