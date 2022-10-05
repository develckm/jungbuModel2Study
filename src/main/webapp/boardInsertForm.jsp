<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록 폼</title>
</head>
<body>
	<header>
		<%@include file="./template/headerNav.jsp" %>
	</header>
	<main style="display: flex; justify-content: center;">
		<div>
			<h1>게시글 등록 폼</h1>
			<p>(성공하면 boardList.do, 실패하면 boardInserForm.jsp)</p>

			<form action="./boardInsert.do" method="post" name="boardInsertForm">
				<p>
					<label>
						제목 :
						<input type="text" name="title" value="글 제목 입니다.">
					</label>
				</p>
				<p>
					<label>
						글쓴이 :
						<%	if(loginUser!=null){//headerNav.jsp 에 추가된 변수 %>
						<input type="text" name="user_id" value="<%=loginUser.getUserId()%>" readonly>
						<%	}else{
								response.sendRedirect("./loginForm.jsp");
							}
						%>
					</label>
				</p>
				<p>	
					<label>
						글내용 :
						<textarea name="contents" rows="" cols="">글 내용 입니다.</textarea>
					</label>
				</p>	
				<p>
					<button type="reset">초기화</button>
					<button>등록</button>
				</p>
			</form>
		</div>
	</main>
	
</body>
</html>