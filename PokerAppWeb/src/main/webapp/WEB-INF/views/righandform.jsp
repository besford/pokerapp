<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>PokerApp | Rig Hands</title>
	</head>
	
	<body>
		<h1>Editing Hands</h1>
		<h2>Please select desired cards for player hands.</h3>
	
		<h3>Table summary:</h3>
		<div>${game.returnRevealedGameState()}</div>
	
		<h3>Card Selection:</h3>
		<div>
			<form action="confirm_rig" method="POST">
				${game.returnCardsForm()}
    			<input type="submit" value="Confirm">
			</form>
		</div>
	</body>
</html>