<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
	<script src="http://code.jquery.com/jquery-2.0.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<meta charset="UTF-8">
<title>登录页面</title>
</head>
<body>
	
	
	
	用户登录login页面 
	
	<form action="/LeaveProcessManagementSystemByXML/loginValidate">
		<input name="username" type="text">
		<input name="password" type="password">
		<input type="submit" value="提交">
	</form>
	
	<#if shiroLoginFailureMessage??>
		${shiroLoginFailureMessage}
	</#if>
</body>
</html>