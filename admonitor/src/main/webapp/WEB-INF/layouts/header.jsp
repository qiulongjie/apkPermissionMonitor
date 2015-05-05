<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="header">
	<div id="title">
	    <h1><a href="${ctx}">Apk监控管理</a><small>掌众传媒</small>
	    <shiro:user>
			<div class="btn-group pull-right">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="icon-user"></i> <shiro:principal property="name"/>
					<span class="caret"></span>
				</a>
			
				<ul class="dropdown-menu">
					<shiro:hasRole name="admin">
						<li><a href="${ctx}/admin/user">Admin Users</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
					<li><a href="${ctx}/apk">Apk权限管理</a></li>
					<li><a href="${ctx}/apk/apkList">Apk管理</a></li>
<%-- 					<li><a href="${ctx}/api">APIs</a></li> --%>
					<li><a href="${ctx}/profile">修改密码</a></li>
					<li><a href="${ctx}/logout">注销</a></li>
				</ul>
			</div>
		</shiro:user>
		</h1>
	</div>
</div>