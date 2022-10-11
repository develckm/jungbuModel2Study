<%@page import="model2_study.com.dto.ReplyDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	Object reply_obj=request.getAttribute("reply");
	ReplyDto reply=null;
	if(reply_obj!=null){
		reply=(ReplyDto)reply_obj;
	}
%>
<form style="background-color: #eff; padding: 5px;"
action="./replyUpdate.do" name="replyUpdate<%=reply.getReply_no()%>" method="post">
	<input type="hidden" name="boardNo" value="<%=reply.getBoard_no()%>">
	<input type="hidden" name="userId" value="<%=reply.getUser_id()%>">
	<input type="hidden" name="replyNo" value="<%=reply.getReply_no()%>">
	<p>
		<label>제목 :
			<input type="text" name="title" value="<%=reply.getTitle()%>">
		</label>
	</p>
	<p>	
		<label>글번호 : <%=reply.getReply_no()%> </label>
		/ <label>글쓴이 : <%=reply.getUser_id()%> </label>
		/ <label>게시일 : <%=reply.getPost_time()%> </label>
	</p>
	<div>
		<label>내용 : 
			<textarea rows="" cols="" name="contents"><%=reply.getContents()%></textarea>
		</label>
	</div>
	<p>
		<button type="reset">초기화</button>
		<button type="button" onclick="replyUpdateAct(event)">수정</button>
		<button type="button" onclick="replyDeletAct(event)">삭제</button>
	</p>
</form>
