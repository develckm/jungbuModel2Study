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
enctype="multipart/form-data"
action="./replyUpdate.do" name="replyUpdate<%=reply.getReply_no()%>" method="post">
	<input type="hidden" name="boardNo" value="<%=reply.getBoard_no()%>">
	<input type="hidden" name="userId" value="<%=reply.getUser_id()%>">
	<input type="hidden" name="replyNo" value="<%=reply.getReply_no()%>">
	<input type="hidden" name="imgPath" value="<%=reply.getImg_path()%>">
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
	<p>
		<label>이미지 :
			<input type="file" name="img">
		</label>
	</p>
	<div style="display: flex;">
		<div style="width: 25%">
			<img alt="" src="./public/img/<%=reply.getImg_path()%>"
			 style="width: 100%; height: 100%; object-fit: cover;">			
		</div>
		<div style="width: 75%">
			<textarea rows="" cols="" name="contents"><%=reply.getContents()%></textarea>
		</div>
	</div>	
	<p>
		<button type="reset">초기화</button>
		<button type="button" onclick="replyUpdateAct(event)">수정</button>
		<button type="button" onclick="replyDeletAct(event)">삭제</button>
	</p>
</form>
