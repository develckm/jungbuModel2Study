package model2_study.com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model2_study.com.dao.BoardPreferDao;
import model2_study.com.dao.BoardPreferDaoImp;
import model2_study.com.dto.BoardPreferDto;
import model2_study.com.dto.UserDto;

@WebServlet("/boardPreferModify.do")
public class BoardPreferModify extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardNo_str=req.getParameter("boardNo");
		String preferBtn_str=req.getParameter("prefer");//1: 좋아요, 2:싫어요 
		HttpSession session=req.getSession();
		Object loginUser_obj=session.getAttribute("loginUser");
		int modify=0;
		//{"modify":-1~3} 
		//1 : 등록 성공 (한번도 좋아요,싫어요를 하지 않았을때 )
		//2 : 수정 성공 (좋아요나 싫어요를 했는데 반대를 눌렀을 때)
		//3 : 삭제 성공 (좋아요나 싫어요를 했는데 같은 것을 눌렀을 때)
		//0 : 실패
		//-1: 로그인 하세요
		BoardPreferDao boardPreferDao=null;
		try {
			if(loginUser_obj!=null) {
				UserDto loginUser=(UserDto)loginUser_obj; 	
				int boardNo=Integer.parseInt(boardNo_str);
				int preferBtn=Integer.parseInt(preferBtn_str);
				boardPreferDao=new BoardPreferDaoImp();
				BoardPreferDto boardPrefer=
						boardPreferDao.detailFindbyUserIdAndBoardNo(loginUser.getUserId(), boardNo);
				if(boardPrefer==null) {//등록
					boardPrefer=new BoardPreferDto();
					boardPrefer.setBoard_no(boardNo);
					boardPrefer.setPrefer(preferBtn);
					boardPrefer.setUser_id(loginUser.getUserId());
					int insert=boardPreferDao.insert(boardPrefer);
					modify=insert;
				}else if(boardPrefer.getPrefer()==preferBtn) { //삭제
					int delete=boardPreferDao.delete(boardPrefer.getBoard_prefer_no());
					if(delete>0)modify=3;
				}else if(boardPrefer.getPrefer()!=preferBtn) { //수정
					boardPrefer.setPrefer(preferBtn);
					int update=boardPreferDao.update(boardPrefer);
					if(update>0)modify=2;	
				}
			}else {
				modify=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(boardPreferDao!=null)boardPreferDao.close();
		}
		
		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().append("{\"modify\":"+modify+",\"preferBtn\":"+preferBtn_str+"}");
	}
}
