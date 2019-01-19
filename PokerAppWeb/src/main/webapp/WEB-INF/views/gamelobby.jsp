<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>PokerApp | Lobby</title>
	</head>
	<body>
		<h1>Lobby</h1>
		
		<h3>Choose which players are human and which players are AI's</h3>	
		<div>
			<form action="newgame" method="POST">
				${game.lobby()}
				<input type="submit" value="Start Game">
			</form>
		</div>
	</body>
</html>