<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>添加Apk权限</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/apk/${action}" method="post"
		class="form-horizontal">
		<input type="hidden" name="id" value="${apkPms.id}" />
		<fieldset>
			<legend>
				<small>apk权限</small>
			</legend>
			<div class="control-group">
				<label for="name" class="control-label">权限名:</label>

				<div class="controls">
					<input type="text" name="pmsName" value="${apkPms.pmsName}">
				</div>

			</div>
			<div class="control-group">
				<label for="phone_num" class="control-label">中文名:</label>

				<div class="controls">
					<input type="text" name=name value="${apkPms.name}">
				</div>

			</div>
			<div class="control-group">
				<label for="phone_num" class="control-label">描述:</label>

				<div class="controls">
					<input type="text" name=pmsDesc value="${apkPms.pmsDesc}">
				</div>

			</div>
			<div class="control-group">
				<label for="task_title" class="control-label">是否高危权限:</label>
				<div class="controls">
					<select name="danger_Flag">
						<option value="0"
							<c:if test="${apkPms.danger_Flag==0}">selected</c:if>>否</option>
						<option value="1"
							<c:if test="${apkPms.danger_Flag==1}">selected</c:if>>是</option>
					</select>

				</div>
			</div>

			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="提交" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="返回" onclick="history.back()" />
			</div>

		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#pmsName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
	<script type="text/javascript">
		
	</script>
</body>
</html>
