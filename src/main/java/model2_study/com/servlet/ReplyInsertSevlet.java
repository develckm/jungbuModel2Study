package model2_study.com.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model2_study.com.dao.ReplyDao;
import model2_study.com.dao.ReplyDaoImp;
import model2_study.com.dto.ReplyDto;

@WebServlet("/replyInsert.do")
public class ReplyInsertSevlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		//ajax로 등록을 요청하기 때문에 json 상태를 응답할 예정
		int insert=0; //응답 {"insert":1}
		ReplyDao replyDao=null;
		try {
			int maxSize=1024*1024*1;
			String path=req.getServletContext().getRealPath("./public/img");
			DefaultFileRenamePolicy dfrp=new DefaultFileRenamePolicy();
			MultipartRequest multiReq=new MultipartRequest(req, path,maxSize,"UTF-8",dfrp);
			
			File imgFile=multiReq.getFile("img");
			String fileName=null;
			if(imgFile!=null && imgFile.exists()) {
				String[]fileTypes=multiReq.getContentType("img").split("/");
				if(fileTypes[0].equals("image")) {
					fileName="reply_"+System.currentTimeMillis()+"_"
								+((int)(Math.random()*10000))+"."+fileTypes[1];
					File newImgFile=new File(path+"/"+fileName);
					imgFile.renameTo(newImgFile);
				}else {
					System.out.println("이미지가 아닌 파일 삭제:"+imgFile.delete());
				}
			}
			String boardNo_str=multiReq.getParameter("boardNo");
			String title=multiReq.getParameter("title");
			String contents=multiReq.getParameter("contents");
			String userId=multiReq.getParameter("userId");

			ReplyDto replyDto=new ReplyDto();
			replyDto.setBoard_no(Integer.parseInt(boardNo_str));
			replyDto.setTitle(title);
			replyDto.setContents(contents);
			replyDto.setUser_id(userId);
			replyDto.setImg_path(fileName);
			System.out.println(replyDto);
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
