<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 폼</title>
</head>
<body>
	<%@include file="./template/headerNav.jsp" %>
	<h1>Spring_board.user 로그인 폼</h1>
	<ul>
		<li>UserLoginServlet.java(/login.do) Controller 호출</li>
		<li>서블릿에서 UserDao.login(id,pw) Model 호출 </li>
		<li>dao의 결과가 null이 아니면 세션 객체 생성 후 홈(Veiw)으로 이동</li>
		<li>dao의 결과가 null이면 form(View)으로 이동</li>
	</ul>
	<div style="display: flex; justify-content: center;">
		<form action="./login.do" method="post">
			<p>
				<label>ID:
					<input type="text"name="user_id" value="awriter">
				</label>
			</p>
			<p>
				<label>PW:
					<input type="password"name="pw" value="1234">
				</label>
			</p>
			<p style="display: flex; justify-content: flex-end;">
				<button>로그인</button>
			</p>		
		</form>
	</div>
</body>
</html>