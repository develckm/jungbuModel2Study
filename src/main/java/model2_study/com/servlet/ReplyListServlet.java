package model2_study.com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model2_study.com.dao.ReplyDao;
import model2_study.com.dao.ReplyDaoImp;
import model2_study.com.dto.ReplyDto;

@WebServlet("/replyList.do")
public class ReplyListServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardNo_str=req.getParameter("boardNo");
		String page_str=req.getParameter("page"); //기본 값이 존재하는 파라미터
		List<ReplyDto> replyList=null;
		String fowardPage="replyList.jsp";
		ReplyDao replyDao=null;
		try {
			int page=(page_str==null)?1:Integer.parseInt(page_str);
			replyDao=new ReplyDaoImp();
			replyList=replyDao.listFindByBoardNo(page, Integer.parseInt(boardNo_str));
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(replyDao!=null)replyDao.close();
		}
		
		if(replyList==null) {
			resp.setContentType("text/html;charset=UTF-8;");
			resp.getWriter().append("<p>검색 결과 없음!</p>");
		}else {
			req.setAttribute("replyList", replyList);
			req.getRequestDispatcher(fowardPage).forward(req, resp);
		}
		
	
	}
}
