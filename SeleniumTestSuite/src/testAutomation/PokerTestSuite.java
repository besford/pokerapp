package testAutomation;

import junit.framework.JUnit4TestAdapter;
import OSTools.OSInfo;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PokerTestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    
    if (OSInfo.getOs() == OSInfo.OS.UNIX)
    	System.setProperty("webdriver.chrome.driver","selenium-3.4/chromedriver");
    if (OSInfo.getOs() == OSInfo.OS.WINDOWS)
    	System.setProperty("webdriver.chrome.driver","selenium-3.4/chromedriver.exe");
    
    /*Tests for ui controls*/
    suite.addTest(new JUnit4TestAdapter(MainPageDisplays.class));
    suite.addTest(new JUnit4TestAdapter(ClickingStartNewGameButtonOnHomepageDisplaysLobby.class));
    suite.addTest(new JUnit4TestAdapter(ClickingStartNewGameButtonOnLobbyDisplaysGame.class));
    suite.addTest(new JUnit4TestAdapter(ClickingStartNewGameButtonOnLobbyWithAIDisplaysStrats.class));
    suite.addTest(new JUnit4TestAdapter(ClickingStartGameButtonOnStratsDisplaysGame.class));
    suite.addTest(new JUnit4TestAdapter(ClickingStandButtonDisplaysUpdatedGame.class));
    suite.addTest(new JUnit4TestAdapter(ClickingDiscardButtonDisplaysDiscardForm.class));
    suite.addTest(new JUnit4TestAdapter(ClickingConfirmDiscardButtonNoneSelectedDisplaysGame.class));
    suite.addTest(new JUnit4TestAdapter(ClickingConfirmDiscardButtonCardsSelectedDisplaysGame.class));
    suite.addTest(new JUnit4TestAdapter(ClickingRigHandsConfirmDisplaysGame.class));
    suite.addTest(new JUnit4TestAdapter(ClickingRigDrawButtonDisplaysRigDrawForm.class));
    suite.addTest(new JUnit4TestAdapter(ClickingRigDrawConfirmDisplaysGame.class));
    //suite.addTest();
    
    /*
    suite.addTest(new JUnit4TestAdapter(ProgressPageDisplaysActionButtons.class));
    suite.addTest(new JUnit4TestAdapter(ClickingStandButtonDisplaysUpdatedGame.class));
    suite.addTest(new JUnit4TestAdapter(ResultsPageDisplaysInformationAndNewGameButton.class));
    suite.addTest(new JUnit4TestAdapter(ClickingNewGameButtonOnResultsPageReturnsToHomePage.class));*/
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
