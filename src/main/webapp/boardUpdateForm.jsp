<%@page import="model2_study.com.dto.BoardImgDto"%>
<%@page import="model2_study.com.dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정 폼</title>
</head>
<body>
	<header>
		<%@include file="./template/headerNav.jsp" %>
	</header>
<%
	Object board_obj=request.getAttribute("board");
	BoardDto board=null;
	if(board_obj!=null){
		board=(BoardDto)board_obj;
	}
	//out.append(board.toString());
%>		
	<main style="display: flex; justify-content: center;">
		<div>
			<h1>게시글 수정 폼</h1>
			<p>(성공하면 boardDetail.do, 실패하면 boardUpdateForm.jsp)</p>

			<form action="./boardUpdate.do" enctype="multipart/form-data" method="post" name="boardUpdateForm">
				<!-- type="hidden" 보이지는 않지만 제출시 넘어가는 파라미터 -->
				<input type="hidden" name="boardNo" value="<%=board.getBoard_no()%>">
				<input type="hidden" name="userId" value="<%=board.getUser_id()%>">
				<p>
					<label>
						번호 : <strong><%=board.getBoard_no()%></strong>
					</label>
				</p>
				<p>
					<label>
						조회수 : <strong><%=board.getViews()%></strong>
					</label>
				</p>
				<p>
					<label>
						등록일 : <strong><%=board.getPost_time()%></strong>
					</label>
				</p>
				<p>
					<label>
						글쓴이 : <strong><%=board.getUser_id()%></strong>
					</label>
				</p>
				<div>
					<p>
						<label>
							이미지 :<input type="file" name="img1">
						</label>
					</p>
					<p>
						<label>
							이미지 :<input type="file" name="img2">
						</label>
					</p>
					<p>
						<label>
							이미지 :<input type="file" name="img3">
						</label>
					</p>
					<p>
						<label>
							이미지 :<input type="file" name="img4">
						</label>
					</p>
					<p>
						<label>
							이미지 :<input type="file" name="img5">
						</label>
					</p>
				
				
				</div>
<style>
#imgContainer{
	display: grid; 
	grid-template-columns: auto auto auto;
	grid-gap: 10px;
}

#imgContainer img{
	width: 100%; 
	height: 200px; 
	object-fit: cover;
}
#imgContainer input[type=checkbox]:checked+img{
	border: 3px solid red;	
}
</style>				
				
				<div id="imgContainer" >
				<%if(board.getBoardImgList()!=null){ 
					for(BoardImgDto boardImg:board.getBoardImgList()){
				%>	
					<label>
						<input type="checkbox" name="boardImgNo" value="<%=boardImg.getBoard_img_no()%>">
						<img alt="삭제 할 이미지" src="./public/img/<%=boardImg.getImg_path()%>">
					</label>
				<%}
				}%>
				</div>
				<p>
					<label>
						제목 :
						<input type="text" name="title" value="<%=board.getTitle()%>">
					</label>
				</p>
				<p>	
					<label>
						글내용 :
						<textarea name="contents" rows="" cols=""><%=board.getContents()%></textarea>
					</label>
				</p>	
				<p>
					<button type="reset">초기화</button>
					<button>수정</button>
					<a href="./boardDelete.do?boardNo=<%=board.getBoard_no()%>">삭제</a>
				</p>
			</form>
		</div>
	</main>
	
</body>
</html>