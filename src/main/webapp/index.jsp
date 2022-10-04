<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>model2 board</title>
</head>
<body>
<%@include file="./template/headerNav.jsp" %>
	<h1>Model2로 로그인이 가능한 게시판을 만들어보자</h1>
	<img width="100%" alt="spring_board_entity" src="./public/img/board_spring_entity.png">
	<ul>
		<li>mysql > spring_board (board_user) 생성</li>
		<li>Model2 : MVC 디자인 패턴(구조상의 약속)
			<ul>
				<li>Model : 웹앱의 서비스인 db 접속 [dba]</li>
				<li>View : 화면 (Html,css,js ..) [frotend]</li>
				<li>Controller : 요청을 처리하는 서블릿 [backend]</li>
				<li>작업을 분할하고 코드를 재사용하기 위해 등장한 구조 (약속된 구조로 구현이 어렵다.)</li>
			</ul>
		</li>
	</ul>
</body>
</html>