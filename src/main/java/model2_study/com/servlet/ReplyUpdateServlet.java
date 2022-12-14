package model2_study.com.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model2_study.com.dao.ReplyDao;
import model2_study.com.dao.ReplyDaoImp;
import model2_study.com.dto.ReplyDto;
import model2_study.com.dto.UserDto;
@WebServlet("/replyUpdate.do")
public class ReplyUpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//forward : replyUpdateForm.jsp
		String replyNo_str=req.getParameter("replyNo");
		String forwardPage="./replyUpdateForm.jsp";
		ReplyDto reply=null;
		ReplyDao replyDao=null;
		try {
			replyDao=new ReplyDaoImp();
			reply=replyDao.detail(Integer.parseInt(replyNo_str));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(replyDao!=null)replyDao.close();
		}
		if(reply==null) {	
			resp.setStatus(404);
		}else {
			req.setAttribute("reply", reply);
			req.getRequestDispatcher(forwardPage)
			.forward(req, resp);
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//action : update 
		int update=0;
		ReplyDao replyDao=null;
		try {
			String path=req.getServletContext().getRealPath("./public/img");
			int maxSize=1024*1024*1;
			DefaultFileRenamePolicy dfrp=new DefaultFileRenamePolicy();
			MultipartRequest multiReq=new MultipartRequest(req, path,maxSize,"UTF-8",dfrp);
			
			String boardNo_str=multiReq.getParameter("boardNo");
			String replyNo_str=multiReq.getParameter("replyNo");
			String userId=multiReq.getParameter("userId");
			String title=multiReq.getParameter("title");
			String contents=multiReq.getParameter("contents");
			String imgPath=multiReq.getParameter("imgPath"); //?????? ?????????
			//????????? ????????? ???????????? ?????? ????????? ?????? ??? ????????????
			File imgFile=multiReq.getFile("img");
			if(imgFile!=null && imgFile.exists()) {
				String[]fileTypes=multiReq.getContentType("img").split("/");
				if(fileTypes[0].equals("image")) {
					File oldImgFile=new File(path+"/"+imgPath); //?????? ????????? ??????
					oldImgFile.delete(); 
					imgPath="reply_"+System.currentTimeMillis()+"_"+((int)(Math.random()*10000))+"."+fileTypes[1];
					File newImgFile=new File(path+"/"+imgPath);
					imgFile.renameTo(newImgFile);						
				
				}else {
					System.out.println("???????????? ?????? ?????? ?????? :"+imgFile.delete() );
				}
			}
			replyDao=new ReplyDaoImp();
			ReplyDto replyDto=new ReplyDto();
			replyDto.setBoard_no(Integer.parseInt(boardNo_str));
			replyDto.setReply_no(Integer.parseInt(replyNo_str));
			replyDto.setUser_id(userId);
			replyDto.setTitle(title);
			replyDto.setContents(contents);
			replyDto.setImg_path(imgPath);
			update=replyDao.update(replyDto);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(replyDao!=null)replyDao.close();
		}		
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().append("{\"update\":"+update+"}");
	}
	//get,post, (fetch,put,delete RESTFull API:???????????? ?????? ???????????? ?????? ????????? ??? ????????? ????????????)
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//fetch????????? ????????? ??? ?????? method(form action????????? ????????? ??? ??????.)
		int delete=0;
		HttpSession session=req.getSession();
		Object loginUser_obj=session.getAttribute("loginUser");
		//delete :-1 ????????? ?????????.
		//delete :-2 ???????????? ?????? ??????.
		//delete :-3 db ??????,???????????? ??????
		String replyNo_str=req.getParameter("replyNo");
		ReplyDao replyDao=null;
		try {
			if(loginUser_obj!=null) {
				UserDto loginUser=(UserDto)loginUser_obj;
				int replyNo=Integer.parseInt(replyNo_str);
				replyDao=new ReplyDaoImp();
				ReplyDto reply=replyDao.detail(replyNo);
				if(reply!=null) {
					if(loginUser.getUserId().equals(reply.getUser_id())) {
						String path=req.getServletContext().getRealPath("./public/img");
						File imgFile=new File(path+"/"+reply.getImg_path());
						System.out.println("????????? ?????? ??????:"+imgFile.delete());
						delete=replyDao.delete(replyNo);									
					}else {
						delete=-2;
					}										
				}
				
			}else {
				delete=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			delete=-3;
		}finally {
			if(replyDao!=null)replyDao.close();
		}
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().append("{\"delete\":"+delete+"}");
		
	}
}
