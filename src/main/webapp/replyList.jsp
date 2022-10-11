<%@page import="java.util.ArrayList"%>
<%@page import="model2_study.com.dto.ReplyDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Object replyList_obj=request.getAttribute("replyList");
List<ReplyDto> replyList=null;
//out.append(replyList_obj.toString());
if(replyList_obj!=null){
	replyList=(ArrayList<ReplyDto>)replyList_obj;
}
%>
<ul>
	<%for(ReplyDto reply:replyList){ %>
	<li id="replyLi<%=reply.getReply_no()%>">
		<p>
			제목 : <%=reply.getTitle() %>
		</p>
		<p>
			<strong><%=reply.getReply_no()%></strong>
			/ <strong><%=reply.getUser_id()%></strong>
			/ <strong><%=reply.getPost_time()%></strong>
			/ <button class="replyUpdateReqBtn" onclick="replyUpdateReq(event)"  value="<%=reply.getReply_no()%>">수정</button>
		</p>
		<!-- onclick=function(event){replyUpdateReq()} -->
		<p>
			<%=reply.getContents()%>
		</p>
	
	</li>
	<%} %>
</ul>
<script>
</script>

</body>
</html>