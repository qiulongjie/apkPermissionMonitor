<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<title>权限详细信息</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">

		</div>
	</div>
	
	<div><a class="btn" href="javascript:history.back()">返回</a></div>
	
	<br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th align="center">名称</th>
				<th align="center">大小(B)</th>
				<th align="center">包名</th>
				<th align="center">版本</th>
				<th align="center">权限总数</th>
				<th align="center">高危权限</th>
				<th align="center">普通权限</th>
				<th align="center">未知权限</th>
			</tr>
		</thead>
		<tbody>
			<tr>		
				<td>${apkInfo.apkName }</td>
				<td>${apkInfo.apkSize }</td>
				<td>${apkInfo.apkPackage }</td>
				<td>${apkInfo.versionCode }</td>
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
			</tr>
		</tbody>
	</table>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th align="center">权限</th>
				<th align="center">名称</th>
				<th align="center">描述</th>
				<th align="center">是否高危</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${apkInfo.apkPermissions}" var="apkPms">
			<tr>		
				<td>${apkPms.pmsName }</td>
				<td>${apkPms.name }</td>
				<td>
					${apkPms.pmsDesc }			
				</td>
				<td>
				<c:if test="${apkPms.danger_Flag=='0'}">否</c:if>
				<c:if test="${apkPms.danger_Flag=='1'}"><font color='red'>是</font></c:if>	
				<c:if test="${apkPms.danger_Flag=='3'}">未知</c:if>	
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
<%-- 	<tags:pagination page="${apkInfos}" paginationSize="5"/> --%>

</body>
</html>
