package model2_study.com.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dao.BoardImgDao;
import model2_study.com.dao.BoardImgDaoImp;
import model2_study.com.dto.BoardDto;
import model2_study.com.dto.BoardImgDto;
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
		BoardImgDao boardImgDao=null;//등록된 이미지들을 서버에서 삭제하기 위해 불러옴
		String msg=null;
		HttpSession session=req.getSession();
		Object loginUser_obj=session.getAttribute("loginUser");
		UserDto loginUser=(loginUser_obj!=null)?(UserDto)loginUser_obj:null;
		
		try {
			if(loginUser!=null) { 
				int boardNo=Integer.parseInt(boardNo_str);
				//로그인 한사람이 글쓴이일 때만 삭제가능
				boardDao=new BoardDaoImp();
				boardImgDao=new BoardImgDaoImp();
				
				BoardDto boardDto=boardDao.detail(boardNo);
				if(boardDto.getUser_id().equals(loginUser.getUserId())) {
					List<BoardImgDto> boardImgList=boardImgDao.boardList(boardNo);
					//서버에 등록된 이미지를 삭제하기 위해 불러옴
					for (BoardImgDto boardImg: boardImgList) {
						String path=req.getServletContext().getRealPath("./public/img");
						File file=new File(path+"/"+boardImg.getImg_path());
						File file2=new File(path+"/thumb/"+boardImg.getImg_path());
						System.out.println("삭제 파일,썸네일 :"+file.delete()+","+file2.delete());
					}
					
					
					int imgDelete=boardImgDao.boardDelete(boardNo); 
					//참조의 제약조건이 delete on restrict 일때 꼭 삭제를 해야 board가 삭제된다.
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
