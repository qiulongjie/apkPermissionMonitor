<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<title>Apk列表</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
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
	
	<div><a class="btn" href="${ctx}/apk/apkList">刷新</a></div>
	<br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<!-- 				<th align="center">ID</th> -->
				<th align="center">名称</th>
				<th align="center">权限总数</th>
				<th align="center">高危权限</th>
				<th align="center">普通权限</th>
				<th align="center">未知权限</th>
				<th align="center">操作</th>
<!-- 				<th align="center">管理</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${apkInfos.content}" var="apkInfo">
			<tr>		
<%-- 				<td>${apkPms.id }</td>		 --%>
				<td>${apkInfo.apkName }</td>
<%-- 				<td><fmt:formatNumber valule="${apkInfo.apkSize/1024/1024 }" pattern="#.##"/></td> --%>

				<td>
					${apkInfo.pmsCount }			
				</td>
				<td>
					${apkInfo.pmsDangerCount }			
				</td>
				<td>
					${apkInfo.pmsNormalCount }			
				</td>
				<td>
					${apkInfo.pmsUnknowCount }			
				</td>
				<td>
				    <a href="${ctx}/apk/showPmsDetail/${apkInfo.id}">查看详情</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${apkInfos}" paginationSize="5"/>

</body>
</html>
