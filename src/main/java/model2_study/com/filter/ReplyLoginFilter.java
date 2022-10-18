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
		
		//FilterChain chain : 요청을 필터에서 처리 완료했을 때 서블릿으로 보내는 매개변수
		//로그인이 안되었을 때 ???
		HttpSession session=req.getSession();
		Object loginUser=session.getAttribute("loginUser");
		if(loginUser!=null) {
			chain.doFilter(request, response);			
		}else {
			//resp.sendRedirect("./login.do"); //ajax에서는 동작하지 않는다.
			resp.setStatus(400); 
			//요청시 필요한 파라미터가 없는 것(세션도 파라미터의 일부로 생각)
		}
	}

}
