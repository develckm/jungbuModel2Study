<%@page import="model2_study.com.dto.BoardPreferDto"%>
<%@page import="model2_study.com.dto.BoardImgDto"%>
<%@page import="model2_study.com.dto.ReplyDto"%>
<%@page import="model2_study.com.dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세</title>
<script src="./public/js/boardDetail.js" defer="defer"></script>
<style type="text/css">
#imgContainer{
    display: grid;
    grid-template-columns: 50% 50%;
    width: 100%;
}
#imgContainer>img{
	width: 100%;
}
</style>
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
		<p style="display: flex; justify-content: flex-end;">
			<a href="./boardUpdate.do?boardNo=<%=board.getBoard_no()%>">게시글 수정</a>
		</p>
		<h2><span><%=board.getBoard_no()%></span>.<%=board.getTitle() %></h2>
		<div>
<style>
#preferContainer button.prefer{
	padding: 10px;
	border-radius: 30px;
}	
#preferContainer button.prefer.active{
    box-shadow: inset -2px -1px 1px 1px rgb(0 0 0 / 80%);
}
#likesBtn{
	background-color: rgb(100,200,255);
}
#badsBtn{
	background-color: rgb(255,100,100);
}

</style>		
<%
Object boardPrefer_obj=request.getAttribute("boardPrefer");
Boolean prefer=null; //true linkes.active, false bads.active
if(boardPrefer_obj!=null){
	BoardPreferDto boardPrefer=(BoardPreferDto)boardPrefer_obj;
	prefer=(boardPrefer.getPrefer()==1)?true:false;
}
%>
			<p id="preferContainer">
				<button id="likesBtn" value="<%=board.getBoard_no()%>" class="prefer 
				<%=(prefer!=null&&prefer)?"active":""%>"
				onclick="boardPreferModify(event,1)"
				>
					좋아요
					<strong>
						<%=board.getLikes()%>
					</strong>
				</button>
				<button id="badsBtn" value="<%=board.getBoard_no()%>" class="prefer 
				<%=(prefer!=null&&!prefer)?"active":""%>"
				onclick="boardPreferModify(event,0)"
				>
					싫어요
					<strong>
						<%=board.getBads()%>
					</strong>
				</button>
			</p>
			<p>
				<strong><%=board.getUser_id()%></strong> 
				<span>(<%=board.getUser().getName() %>)</span>
				/ <span><%=board.getUser().getEmail() %></span>
			</p>
			<p>
				<span><%=board.getPost_time()%></span>
				/ <span><%=board.getViews()%></span>
			</p>
		</div>
		
		<div id="imgContainer">
			<%if(board.getBoardImgList()!=null){
				for(BoardImgDto boardImg : board.getBoardImgList()){	
			%>
				<img src="./public/img/<%=boardImg.getImg_path()%>" alt="게시글 이미지" >
			<%} 
			}%>
		</div>
		
		<div>
			<%=board.getContents() %>
		</div>
		<p>
			<button id="replysReload" value="<%=board.getBoard_no()%>">새로고침</button>
		</p>
		<div style="background-color: #eee">
			<h3>댓글 등록</h3>
			<form action="./replyInsert.do" enctype="multipart/form-data"
					name="replyInsert" method="post">
				<input type="hidden" name="boardNo" value="<%=board.getBoard_no()%>">
				<p>
					<label>제목 :
						<input type="text" name="title" value="리플제목">
					</label>
				</p>
				<p>	
					<label>글쓴이 :
						<input type="text" name="userId" value="awriter">
					</label>
				</p>
				<p>	
					<label>이미지 :
						<input type="file" name="img">
					</label>
				</p>
				<div>
					<label>내용 : 
						<textarea rows="" cols="" name="contents">글내용입니다.</textarea>
					</label>
				</div>
				<p><button>등록</button></p>
			</form>
		</div>
		
		
		<section id="replysContainer">
			<ul>
				<%for( ReplyDto reply : board.getReplyList()){ %>
					<li><%=reply%></li>
				<%} %>
			</ul>
		</section>
 	</main>
</body>
</html>