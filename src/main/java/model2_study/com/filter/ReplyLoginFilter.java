package model2_study.com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model2_study.com.dao.ReplyDao;
import model2_study.com.dao.ReplyDaoImp;
import model2_study.com.dto.ReplyDto;
import model2_study.com.dto.UserDto;
@WebFilter(urlPatterns = {"/replyInsert.do","/replyUpdate.do"})
public class ReplyLoginFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		String replyNo_str=req.getParameter("replyNo");
		//특정 요청을 서블릿으로 가기전에 한번 실행해주는 필터
		System.out.println("LoginFilter.doFilter() :"+req.getMethod());	
		System.out.println("LoginFilter.doFilter() :"+replyNo_str);	
		int status=0; //-1 로그인이 안된 상태,-2 글쓴이가 아닌것, 1 글쓴이가 로그인 한 상태
		//FilterChain chain : 요청을 필터에서 처리 완료했을 때 서블릿으로 보내는 매개변수
		//로그인이 안되었을 때 ???
		HttpSession session=req.getSession();
		Object loginUser_obj=session.getAttribute("loginUser");
		ReplyDao replyDao=null;
		try {
			if(loginUser_obj!=null) {
				replyDao=new ReplyDaoImp();
				ReplyDto reply=replyDao.detail(Integer.parseInt(replyNo_str));
				if(reply!=null) {
					UserDto loginUser=(UserDto)loginUser_obj;
					status=(reply.getUser_id().equals(loginUser.getUserId()))?1:-2;
				}
			}else {
				status=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(status>0) {
			chain.doFilter(request, response);			
		}else {
			//resp.sendRedirect("./login.do"); //ajax에서는 동작하지 않는다.
			resp.setStatus(400); 
			//요청시 필요한 파라미터가 없는 것(세션도 파라미터의 일부로 생각)
			resp.getWriter().append("{\"status\":"+status+"}");
		}
	}

}
