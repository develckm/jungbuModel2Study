package model2_study.com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dto.BoardDto;

@WebServlet("/boardDetail.do")
public class BoardDetailServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//이페이지는 파라미터가 없으면 동작할 수 없다!!!!
		String boardNo_str=req.getParameter("boardNo");
		BoardDto boardDto=null;
		String errorPage="./boardList.do";//실패시 리스트로 이동
		String forwardPage="./boardDetail.jsp"; //성공시 출력할 view 페이지
		BoardDao boardDao=null;
		try {
			int boardNo=Integer.parseInt(boardNo_str);
			boardDao=new BoardDaoImp();
			boardDto=boardDao.detail(boardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(boardDto==null) {
			resp.sendRedirect(errorPage);
		}else {
			req.setAttribute("board", boardDto);
			req.getRequestDispatcher(forwardPage).forward(req, resp);
		}
		
	}
}
