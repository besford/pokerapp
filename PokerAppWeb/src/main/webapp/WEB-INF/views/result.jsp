<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>PokerApp | Results</title>
	</head>
	<body>
		<h1>Game Over!</h1>
		<h2>Summary:</h2>
		
		<h4>Winning Hand:</h4>
		<div>${game.resolve()}</div>
		
		<h4>Rankings:</h4>
		<div>${game.getLeaderBoard()}</div>

		<h1></h1>
		<h3>Would you like you play again?</h3>
		<div>
			<form action="reset" method="POST">
				<input value="Start new game" name="reset" type="submit">
			</form>
		</div>

</body>
</html>