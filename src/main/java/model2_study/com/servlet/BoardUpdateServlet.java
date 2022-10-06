package model2_study.com.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dto.BoardDto;

@WebServlet("/boardUpdate.do")
public class BoardUpdateServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//updateForm.jsp 를 forward  
		String boardNo_str=req.getParameter("boardNo");
		BoardDao boardDao=null;
		BoardDto boardDto=null;
		try {
			boardDao=new BoardDaoImp();
			boardDto=boardDao.detail(Integer.parseInt(boardNo_str));
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("board", boardDto);
		req.getRequestDispatcher("./boardUpdateForm.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//updateForm.jsp 에서 제출된 양식을 처리하는 곳
		req.setCharacterEncoding("UTF-8");
		String boardNo_str=req.getParameter("boardNo");
		String userId=req.getParameter("userId");
		String title=req.getParameter("title");
		String contents=req.getParameter("contents");

		BoardDao boardDao=null;
		int update=0;
		String redirectPage="./boardDetail.do?boardNo="+boardNo_str;
		String errorPage="./boardUpdate.do?boardNo="+boardNo_str; 
	
		try {
			boardDao=new BoardDaoImp();
			BoardDto boardDto=new BoardDto();
			boardDto.setBoard_no(Integer.parseInt(boardNo_str));
			boardDto.setUser_id(userId);
			boardDto.setTitle(title);
			boardDto.setContents(contents);
			System.out.println(boardDto);
			update=boardDao.update(boardDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(update>0) {
			resp.sendRedirect(redirectPage);
		}else {
			req.getSession().setAttribute("msg", "수정실패");
			resp.sendRedirect(errorPage);
		}
	}
}
