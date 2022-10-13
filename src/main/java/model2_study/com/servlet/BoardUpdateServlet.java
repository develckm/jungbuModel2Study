package model2_study.com.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dao.BoardImgDao;
import model2_study.com.dao.BoardImgDaoImp;
import model2_study.com.dto.BoardDto;
import model2_study.com.dto.BoardImgDto;
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
		BoardImgDao boardImgDao=null;
		BoardDto boardDto=null;
		String msg=null; //오류가 있을 때 메시지 작성
		try {
			int boardNo=Integer.parseInt(boardNo_str);
			//데이터 베이스를 접속해서 하나의 테이블에 하나의 쿼리를 실행하는 집단을 DbAccessObject라하고 
			//쿼리를 여러번 실행하는 것을 하나의 서비스로 제공하는 집단을 Service or Transaction 이라고도 한다.
			boardDao=new BoardDaoImp();
			boardDto=boardDao.detail(boardNo);
			if(boardDto!=null) {
				boardImgDao=new BoardImgDaoImp();
				List<BoardImgDto> boardImgList=boardImgDao.boardList(boardNo);
				boardDto.setBoardImgList(boardImgList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(boardDao!=null)boardDao.close();
			if(boardImgDao!=null)boardImgDao.close();
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
		BoardDao boardDao=null;
		BoardImgDao boardImgDao=null;
		int update=0;
		int imgDelete=0;
		String redirectPage="./boardDetail.do?boardNo=";
		String errorPage="./boardList.do"; 
		String path=req.getServletContext().getRealPath("./public/img");
		int maxSize=1024*1024*10;
		DefaultFileRenamePolicy dfp=new DefaultFileRenamePolicy();
		try {
			MultipartRequest mulitReq=new MultipartRequest(req, path, maxSize, "UTF-8",dfp);
			
			String boardNo_str=mulitReq.getParameter("boardNo");
			String userId=mulitReq.getParameter("userId");
			String title=mulitReq.getParameter("title");
			String contents=mulitReq.getParameter("contents");
			String[]boardImgNos=mulitReq.getParameterValues("boardImgNo");//boardImgNo=1&boardImgNo=2&boardImgNo=3
			redirectPage+=boardNo_str;
			errorPage="./boardUpdate.do?boardNo="+boardNo_str; 
			
			Iterator keys=(Iterator)mulitReq.getFileNames();
			List<String> fileNameList=new ArrayList<String>();
			while(keys.hasNext()) {
				String key=keys.next().toString();
				File imgFile=mulitReq.getFile(key);
				if(imgFile!=null && imgFile.exists()) {
					String [] fileType=mulitReq.getContentType(key).split("/");   //  image/png, application/json
					if(!fileType[0].equals("image")||!(fileType[1].equals("png")||fileType[1].equals("jpeg"))) {
						imgFile.delete(); //image가 아닌 것은 삭제
					}else {
						String random=(int)(Math.random()*10000)+"";
						String fileName="board_"+System.currentTimeMillis()+"_"+random+"."+fileType[1];
						File newFile=new File(path+"/"+fileName);
						File newThumbFile=new File(path+"/thumb/"+fileName);

						imgFile.renameTo(newFile);
						fileNameList.add(fileName);
						
						BufferedImage originImg=ImageIO.read(newFile);
						BufferedImage thumbImg=new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
						thumbImg.getGraphics().drawImage(originImg, 0, 0, 100, 100, null);
						ImageIO.write(thumbImg, fileType[1], newThumbFile);
					}
				}
			}
			
			boardDao=new BoardDaoImp();
			BoardDto boardDto=new BoardDto();
			int boardNo=Integer.parseInt(boardNo_str);
			boardDto.setBoard_no(boardNo);
			boardDto.setUser_id(userId);
			boardDto.setTitle(title);
			boardDto.setContents(contents);
			update=boardDao.update(boardDto);
			if(update>0 && boardImgNos!=null) { //이미지 파일과 db 삭제 
				boardImgDao=new BoardImgDaoImp();
				for(String boardImgNo_str:boardImgNos) {
					int boardImgNo=Integer.parseInt(boardImgNo_str);
					BoardImgDto boardImg=boardImgDao.detail(boardImgNo);
					File file=new File(path+"/"+boardImg.getImg_path());
					File file2=new File(path+"/thumb/"+boardImg.getImg_path());
					if(file.delete() && file2.delete()) {
						imgDelete+=boardImgDao.delete(boardImgNo); //db에 등록된 이미지 경로 삭제
					}
				}
			}
			if(update>0 && fileNameList.size()>0) {
				boardImgDao=new BoardImgDaoImp();
				int boardImgCount=boardImgDao.boardCount(boardNo);
				//5개이하로만 저장가능
				int insertCount=(5-boardImgCount);
				for(String fileName:fileNameList) {
					if(--insertCount<0) {
						File file=new File(path+"/"+fileName);
						File file2=new File(path+"/thumb/"+fileName);
						System.out.println("이미지가5개보다 많아서 삭제:"+file.delete()+"."+file2.delete());
					}else {
						BoardImgDto boardImg=new BoardImgDto();
						boardImg.setImg_path(fileName);
						boardImg.setBoard_no(boardNo);
						boardImgDao.insert(boardImg);						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("이미지 삭제:"+imgDelete);
		if(update>0) {
			resp.sendRedirect(redirectPage);
		}else {
			req.getSession().setAttribute("msg", "수정실패");
			resp.sendRedirect(errorPage);
		}
		
	}
}
