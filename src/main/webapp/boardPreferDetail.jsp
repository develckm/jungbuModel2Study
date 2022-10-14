<%@page import="model2_study.com.dto.BoardDto"%>
<%@page import="model2_study.com.dto.BoardPreferDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
Object boardPrefer_obj=request.getAttribute("boardPrefer");
Object board_obj=request.getAttribute("board");
BoardDto board=null;
if(board_obj!=null){
	board=(BoardDto)board_obj;
	Boolean prefer=null; //true linkes.active, false bads.active
	if(boardPrefer_obj!=null){
		BoardPreferDto boardPrefer=(BoardPreferDto)boardPrefer_obj;
		prefer=(boardPrefer.getPrefer()==1)?true:false;
	}
	%>
<button id="likesBtn" value="<%=board.getBoard_no()%>" class="prefer 
<%=(prefer!=null&&prefer)?"active":""%>"onclick="boardPreferModify(event,1)">
	좋아요
	<strong><%=board.getLikes()%></strong>
</button>
<button id="badsBtn" value="<%=board.getBoard_no()%>" class="prefer 
<%=(prefer!=null&&!prefer)?"active":""%>"onclick="boardPreferModify(event,0)">
	싫어요
	<strong><%=board.getBads()%></strong>
</button>
<%}%>