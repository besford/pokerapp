<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PokerApp | Discard Cards</title>
</head>
<body>
	<h1>Please select which cards you wish to discard.</h1>
	<div>${game.returnPlayersHand()}</div>
	
	<form action="confirm_discard" method="POST">
		<input type="checkbox" name="cardChoices" value="1">1
		<input type="checkbox" name="cardChoices" value="2">2
		<input type="checkbox" name="cardChoices" value="3">3
		<input type="checkbox" name="cardChoices" value="4">4
		<input type="checkbox" name="cardChoices" value="5">5
		<br>
    	<input type="submit" value="Confirm">
	</form>
</body>
</html>