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
import model2_study.com.dto.UserDto;

@WebServlet("/boardUpdate.do")
public class BoardUpdateServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//updateForm.jsp 를 forward  
		String boardNo_str=req.getParameter("boardNo");
		Object loginUser_obj=req.getSession().getAttribute("loginUser");
		UserDto loginUser=(loginUser_obj!=null)?(UserDto)loginUser_obj:null;
	
		BoardDao boardDao=null;
		BoardDto boardDto=null;
		String msg=null; //오류가 있을 때 메시지 작성
		try {
			boardDao=new BoardDaoImp();
			boardDto=boardDao.detail(Integer.parseInt(boardNo_str));		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//삼항연산자의 중첩 
		msg=(boardDto==null)?"조회실패":
				(loginUser==null)?"로그인하세요":
					(!loginUser.getUserId().equals(boardDto.getUser_id()))?"작성자만 수정 가능":
						null;
		if(msg!=null) {
			req.getSession().setAttribute("msg", msg);
			resp.sendRedirect("./boardList.do");			
		}else {
			System.out.println("오류가 없어서 폼으로 이동!!");
			req.setAttribute("board", boardDto);
			req.getRequestDispatcher("./boardUpdateForm.jsp").forward(req, resp);
		}
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
