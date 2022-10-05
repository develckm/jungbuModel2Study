<%@page import="model2_study.com.dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세</title>
</head>
<body>
<%
Object board_obj=request.getAttribute("board");
BoardDto board=null;
if(board_obj!=null){
	board=(BoardDto)board_obj;
}else{
	response.sendRedirect("./boardList.do");
}

%>
	<header>
		<%@include file="./template/headerNav.jsp" %>
	</header>
	<main style="width: 80%; margin: 0 auto;">
		<h1>게시글 상세</h1>
		<h2><span><%=board.getBoard_no()%></span>.<%=board.getTitle() %></h2>
		<div>
			<p>
				<strong><%=board.getUser_id()%></strong> 
				<span>()</span> 
				/ <span><%=board.getPost_time()%></span>
				/ <span><%=board.getViews()%></span>
			</p>
		</div>
		<div>
			<%=board.getContents() %>
		</div>
	</main>
</body>
</html>