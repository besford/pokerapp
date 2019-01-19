<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Game in progress</title>
		<link rel=stylesheet href="https://s3-us-west-2.amazonaws.com/colors-css/2.2.0/colors.min.css">
   	</head>
	<body>
		<h1>AI Game</h1>
		<h3>Current Table:</h3>
		<div>${game.returnAIGameState()}</div>
		
		<div>	
    		<form action="move" method="POST">
				<input value="Do AI Turns" name="move" type="submit">
    			<input value="Rig Hands" name="move" type="submit">
    		</form>
		</div>
	</body>
</html>