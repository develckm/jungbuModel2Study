<%@page import="model2_study.com.dto.PagingDto"%>
<%@page import="model2_study.com.dto.BoardDto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 리스트</title>
<style>
#boardList{
	border-collapse: collapse;
}
#boardList>tbody>tr>td{
	border-bottom: 1px solid;
	padding: 5px 8px;
}
#boardList>tbody>tr:hover{
	cursor: pointer;
	background-color: #eee;
}
main{
	display: flex;
	justify-content: center;
}
h1 {
	margin-left: 40px;
}
</style>	
</head>
<body>
	<%@include file="./template/headerNav.jsp" %>
	<h1>
		게시글 리스트
	</h1>
	<p style="display: flex; justify-content: flex-end; margin-right: 50px;">
		<a href="./boardInsert.do">
				게시글 등록
		</a>
	</p>
	<!--
		a : 검색엔진의 검색 우선 순위를 올리는 용도로 사용될 수 있음 (onclick으로 link를 만드는 것을 권장 하지 않는다.)
		시멘틱요소 : 의미만 있는 블럭 요소 (개발자와 검색엔진이 사이트의 구성을 파악하는 데 사용)
	-->
	<main>
		<table id="boardList">
			<thead>
				<tr>
					<th>번호</th><th>제목</th><th>이미지</th>
					<th>게시일</th><th>글쓴이</th><th>조회</th>
					<th>댓글</th><th>likes</th><th>bads</th>
				</tr>
			</thead>
			<tbody>
			
		<%
		Object boardList_obj=request.getAttribute("boardList"); 
		if(boardList_obj!=null){
			List<BoardDto> boardList=(List<BoardDto>)boardList_obj;
			for(BoardDto board: boardList){
				//out.append("<p>"+board+"</p>");
				%>
				<tr onclick="location.href='./boardDetail.do?boardNo=<%=board.getBoard_no()%>'">
					<td><%=board.getBoard_no()%></td>
					<td><%=board.getTitle()%></td>
					<td>
						<img style="width: 50px" alt="이미지" src="public/img/thumb/<%=board.getThumbPath()%>">
					</td>
					<td><%=board.getPost_time()%></td>
					<td><%=board.getUser_id()%></td>
					<td><%=board.getViews()%></td>
					<td><%=board.getReplyCount()%></td>
					<td><%=board.getLikes()%></td>
					<td><%=board.getBads()%></td>
					
				</tr>
				<% 
			}
		}
		%>
			
			</tbody>
		</table>
	</main>
<style>
#pagingNav{
	display: flex;
	justify-content: center;
}
#pagingNav .active{
	border: 1px solid;
}
#pagingNav>ul{
	display: flex;
	list-style: none;
}
#pagingNav li{
	padding: 5px
}
</style>	
<% 
Object paging_obj=request.getAttribute("paging");
if(paging_obj!=null){
	PagingDto paging=(PagingDto)paging_obj;
	//out.append(paging.toString());
%>		
	<nav id="pagingNav">
		<ul>
			<li>
				<a href="?page=1">
					First
				</a>
			</li>
		
			<%if(paging.isPrev()) {%>
			<li>
				<a href="?page=<%=paging.getPrevPage()%>">
					Prev
				</a>
			</li>
			<%} %>
			<%for(int i=paging.getStartPage(); i<=paging.getEndPage(); i++){ %>
				<%if(i==paging.getPage()){ %>
					<li class="active"><a href="?page=<%=i%>"><%=i%></a></li>
				
				<%}else{ %>
					<li><a href="?page=<%=i%>"><%=i%></a></li>
				<%} %>
			<%} %>
			
			<%if(paging.isNext()) {%>
			<li>
				<a href="?page=<%=paging.getNextPage()%>">
					Next
				</a>
			</li>
			<%} %>
			<li>
				<a href="?page=<%=paging.getTotalPage()%>">
					Last
				</a>
			</li>
		
		</ul>
	</nav>
<%} %>	
</body>
</html>