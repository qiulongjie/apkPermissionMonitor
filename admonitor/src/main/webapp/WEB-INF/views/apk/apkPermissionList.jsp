<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Apk权限管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<c:if test="${not empty error}">
		<div id="error" class="alert alert-error"><button data-dismiss="alert" class="close">×</button>${error}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<%-- <form class="form-search" action="#">
				<label>名称：</label> <input type="text" name="search_LIKE_title"
					class="input-medium" value="${param.search_LIKE_title}">
				<button type="submit" class="btn" id="search_btn">Search</button>
			</form> --%>
		</div>
<%-- 	    <tags:sort/> --%>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<!-- 				<th align="center">ID</th> -->
				<th align="center">权限</th>
				<th align="center">名称</th>
				<th align="center">描述</th>
				<th align="center">是否高危</th>
<!-- 				<th align="center">管理</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${apkPermissions.content}" var="apkPms">
			<tr>		
<%-- 				<td>${apkPms.id }</td>		 --%>
				<td><a href="${ctx}/apk/update/${apkPms.id}">${apkPms.pmsName }</a></td>
				<td>${apkPms.name }</td>
				<td>
					${apkPms.pmsDesc }			
				</td>
				<td>
				<c:if test="${apkPms.danger_Flag=='0'}">否</c:if>
				<c:if test="${apkPms.danger_Flag=='1'}"><font color='red'>是</font></c:if>		
				</td>
<!-- 				<td> -->
<%-- 				   <a href="${ctx}/apk/delete/${apkPms.id}">删除</a> --%>
<!-- 				</td> -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${apkPermissions}" paginationSize="10"/>

	<div><a class="btn" href="${ctx}/apk/createPms">添加apk权限</a></div>
</body>
</html>
