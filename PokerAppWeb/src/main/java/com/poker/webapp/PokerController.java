package com.poker.webapp;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("game")
public class PokerController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String frontPage(HttpSession session, SessionStatus status) {	
		status.setComplete();
		session.removeAttribute("game");
		return "home";
	}
	
	@RequestMapping(value = "/newgame", method = RequestMethod.POST)
    public String startNewGame(HttpSession session, @RequestParam Map<String, String> params){
		Game game = (Game) session.getAttribute("game");
		for (Map.Entry<String, String> entry : params.entrySet())
			game.updatePlayerType(Integer.parseInt(entry.getKey()), entry.getValue());
		
		if (game.numberOfAIPlayers == 0)
			return getGameInProgress(game);
		else
			return "strategyform";
    }
	
	@RequestMapping(value = "/lobby", method = RequestMethod.POST)
	public String gameLobby(HttpSession session, @RequestParam("numPlayers") String numPlayers) {
		int num = Integer.valueOf(numPlayers);
		Game game = new Game(num);
		session.setAttribute("game", game);
		return "gamelobby";
	}
	
	@RequestMapping(value = "/move", method = RequestMethod.POST)
	public String move(HttpSession session, @RequestParam("move") String move) {
		Game game = (Game) session.getAttribute("game");
		if (move.toLowerCase().equals("stand")) {
			game.playerStand();
			return getGameInProgress(game);
		} 
		else if (move.toLowerCase().equals("discard"))
			return "discardform";
		else if (move.toLowerCase().equals("rig hands"))
			return "righandform";
		else if (move.toLowerCase().equals("rig next draw"))
			return "rigdrawform";
		else if (move.toLowerCase().equals("confirm ai move")) {
			game.procTurn();
			return getGameInProgress(game);
		}
		else
			return getGameInProgress(game);
	}
	
	private String getGameInProgress(Game game) {
		if (game.getActivePlayerId() == 0)
			return "result";
		else 
			return "gameinprogress";
	}
	
	@RequestMapping(value = "/confirm_discard", method = RequestMethod.POST)
	public String confirmDiscard(HttpSession session, @RequestParam(value = "cardChoices", required = false) String selectedCards) {
		Game game = (Game) session.getAttribute("game");
		
		if (selectedCards != null) {
			String[] strArr = selectedCards.split(",");
			int[] cards = new int[strArr.length];
			for (int i = 0; i < cards.length; i++) {
				cards[i] = Integer.parseInt(strArr[i]);
				cards[i] -= 1;
			}
			
			game.playerDiscardsCards(cards);
		}
		
		return getGameInProgress(game);
	}
	
	@RequestMapping(value = "/confirm_rig", method = RequestMethod.POST)
	public String confirmRig(HttpSession session, @RequestParam Map<String, String> params) {
		Game game = (Game) session.getAttribute("game");
		game.rigPlayerHands(params);
		return getGameInProgress(game);
	}
	
	@RequestMapping(value = "/confirm_rig_draw", method = RequestMethod.POST)
	public String confirmRigDraw(HttpSession session, @RequestParam Map<String, String> params) {
		Game game = (Game) session.getAttribute("game");
		game.rigPlayerDraw(params);
		return getGameInProgress(game);
	}
	
	@RequestMapping(value = "/confirm_strats", method = RequestMethod.POST)
	public String confirmStrategies(HttpSession session, @RequestParam Map<String, String> params) {
		Game game = (Game) session.getAttribute("game");
		for (Map.Entry<String, String> entry : params.entrySet())
			game.updateAIStrategies(Integer.parseInt(entry.getKey()), entry.getValue());
		return getGameInProgress(game);
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String reset(HttpSession session, SessionStatus status) {
		status.setComplete();
		session.removeAttribute("game");
		return "home";
    }
	
}