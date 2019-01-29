<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部署管理首页显示deployHome</title>
</head>
<body>


	
	<form action="/LeaveProcessManagementSystemByXML/workflow/newdeploy" enctype="multipart/form-data" method="post">
		
		
		<div align="left" >
			流程名称：<input type="text" name="filename" style="width:200px;"/><br />
			流程文件：<input type="file" name="file" style="width:200px;"/><br />
			<input type="submit" value="上传流程"/>
			
		</div>
		
	</form>


</body>
</html>