package model2_study.com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/boardFileUp.do")
public class BoardInsertFileUpServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imgPath=req.getServletContext().getRealPath("./public/img"); //서블릿의 실제경로
		//톰캣서버 기본설정이 웹앱을 배포하는 것이기 때문에 웹앱을 배포하지 않도록 설정해야 저장되는 이미지를 볼 수 있다.
		//Server modules without publishing 을 설정해야 함
	
		System.out.println(imgPath);
		int maxSize=1024*1024*1; //1mb 이상 저장 할 수 없음(IOException)
		DefaultFileRenamePolicy dfp=new DefaultFileRenamePolicy();//같은 이름의 파일이 있으면 숫자를 붙여주는 객체 
		try {
			MultipartRequest mulitReq=new MultipartRequest(req, imgPath, maxSize , "UTF-8" , dfp);
			//MultipartRequest : 파일 blob은 저장하고 문자열 blob은 파라미터 처리를 해준다.
			File imgFile=mulitReq.getFile("img"); //저장된 파일
			String[]imgTypes=mulitReq.getContentType("img").split("/");//  image/png or image/jpeg
			String imgType=imgTypes[0];
			String imgExt=imgTypes[1];
			System.out.println(imgType);
			if(!imgType.equals("image")) { //이미지가 아닌것을 삭제
				boolean delete=imgFile.delete();
				System.out.println("이미지가 아닌 파일 삭제: "+delete);
			}else { //저장된 파일의 이름 규칙을 정하는 것 board_1665540031081_?????.png
				String random=(int)(Math.random()*100000) +"";
				String fileName="board_"+System.currentTimeMillis()+"_"+random+"."+imgExt;
				System.out.println(fileName);
				imgFile.renameTo(new File(imgPath+"/"+fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
