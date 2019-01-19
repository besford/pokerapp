<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>PokerApp | Rig Draw</title>
	</head>
	
	<body>
		<h1>Rig Next Draw</h1>
		<h2>Please select desired card for next draw.</h3>

		<h3>Card Selection:</h3>
		<div>
			<form action="confirm_rig_draw" method="POST">
				${game.returnNextDrawForm()}
    			<input type="submit" value="Confirm">
			</form>
		</div>
	</body>
</html>