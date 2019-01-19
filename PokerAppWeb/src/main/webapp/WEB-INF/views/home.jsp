<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
	<head>
		<title>PokerApp</title>
	</head>
	<body>
		<h1>Welcome to PokerApp</h1>

		<h3>How many players do you want to play?</h3>
		<form action="lobby" method="POST">
			<input type="radio" name="numPlayers" value="2" checked>2 Players
			<br>
			<input type="radio" name="numPlayers" value="3">3 Players
			<br>
			<input type="radio" name="numPlayers" value="4">4 Players
			<br>
			<h1></h1>
    		<input type="submit" value="Start new game">
		</form>
</body>
</html>
