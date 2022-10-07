package model2_study.com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model2_study.com.dao.ReplyDao;
import model2_study.com.dao.ReplyDaoImp;
import model2_study.com.dto.ReplyDto;

@WebServlet("/replyInsert.do")
public class ReplyInsertSevlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		//ajax로 등록을 요청하기 때문에 json 상태를 응답할 예정
		String boardNo_str=req.getParameter("boardNo");
		String title=req.getParameter("title");
		String contents=req.getParameter("contents");
		String userId=req.getParameter("userId");
		System.out.println(boardNo_str);
		System.out.println(title);
		System.out.println(contents);
		System.out.println(userId);
		int insert=0; //응답 {"insert":1}
		ReplyDao replyDao=null;
		try {
			ReplyDto replyDto=new ReplyDto();
			replyDto.setBoard_no(Integer.parseInt(boardNo_str));
			replyDto.setTitle(title);
			replyDto.setContents(contents);
			replyDto.setUser_id(userId);
			
			replyDao=new ReplyDaoImp();
			insert=replyDao.insert(replyDto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(replyDao!=null)replyDao.close();
		}
		
		
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().append("{\"insert\":"+insert+"}");
		//resp.sendRedirect("./boardDetail.do?boardNo="+boardNo_str);
	}
}
