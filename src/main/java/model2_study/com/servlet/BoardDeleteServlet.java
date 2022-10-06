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

@WebServlet("/boardDelete.do")
public class BoardDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardNo_str=req.getParameter("boardNo");
		System.out.println(this.getClass()+"/"+boardNo_str);
		String redirectPage="./boardList.do";
		String errorPage="./boardUpdate.do?boardNo="+boardNo_str;
		int delete=0;
		BoardDao boardDao=null;
		String msg=null;
		HttpSession session=req.getSession();
		Object loginUser_obj=session.getAttribute("loginUser");
		UserDto loginUser=(loginUser_obj!=null)?(UserDto)loginUser_obj:null;
		
		try {
			if(loginUser!=null) { 
				int boardNo=Integer.parseInt(boardNo_str);
				//로그인 한사람이 글쓴이일 때만 삭제가능
				boardDao=new BoardDaoImp();
				BoardDto boardDto=boardDao.detail(boardNo);
				if(boardDto.getUser_id().equals(loginUser.getUserId())) {
					delete=boardDao.delete(boardNo);									
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(boardDao!=null)boardDao.close();
		}
		
		if(delete>0) {
			msg="삭제성공";
			resp.sendRedirect(redirectPage);
		}else {
			msg="삭제실패(삭제된 레코드 or db 접속 오류)";
			resp.sendRedirect(errorPage);
		}
		session.setAttribute("msg", msg);
	}
}
