<%@page import="model2_study.com.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" href="./public/css/headerNav.css">    
<nav id="headerNav">
	<ul>
		<li><a href="./">HOME</a></li>
	
		<li><a href="./userList.do">유저 리스트</a></li>
		<li><a href="./boardList.do">게시글 리스트</a></li>
	</ul>
<%
Object loginUser_obj=session.getAttribute("loginUser");
Object msg=session.getAttribute("msg");
UserDto loginUser=null;
if(msg!=null){
%>
	<script>alert("<%=msg%>")</script>	
<%
	session.removeAttribute("msg");
}%>	
	<div>
		<%if(loginUser_obj==null){ %>
		<a href="./login.do">로그인</a>
		<%}else{ 
			loginUser=(UserDto)loginUser_obj;
		%>
		<div>
			<a><%=loginUser.getName()%>(<%=loginUser.getUserId()%>)님 로그인</a>
			<a href="./logout.do">로그아웃</a>
		</div>
		<%} %>
	</div>
</nav>
