package net.atos.mm.formation.tapestry.pages;

import java.util.Date;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Retain;

public class Welcome
{

    /**
     * This variable is used to store in session the first date access on this
     * page
     */
    @Persist
    private Date sessionStart;

    /**
     * This variable is used to store in session the last message from Hilo Game
     */
    @Persist("flash")
    private String messageFromHilo;

    /**
     * This object can be returned by event method to redirect to the
     * corresponding page. This page must be injected by Tapestry
     */
    @InjectPage
    private Guess guess;

    /**
     * This variable must be initialized only once for this page instance and
     * not be deleted after.
     */
    @Retain
    private Long seed;

    @OnEvent("activate")
    public void initializeRandomizer()
    {
	if (seed == null)
	{
	    seed = System.currentTimeMillis();
	    System.out.println("Initialize randomizer with seed :" + seed);
	}
	if (sessionStart == null)
	{
	    sessionStart = new Date();
	    System.out.println("Initialize first page access time :" + sessionStart);
	}
    }

    public String getMessageFromHilo()
    {
	return messageFromHilo;
    }

    public void setMessageFromHilo(String messageFromHilo)
    {
	this.messageFromHilo = messageFromHilo;
    }

    public Date getSessionStart()
    {
	return sessionStart;
    }

    public void setSessionStart(Date sessionStart)
    {
	this.sessionStart = sessionStart;
    }

    /**
     * This method must be call on actionlink to setup the hilo Game and
     * redirect the user to the Guess Page.
     * 
     * @return Guess Tapestry handle this object to redirect the user to thus pa
     */
    @OnEvent(value = "action", component = "hilo")
    private Guess startHiloGame()
    {

	System.out.println("Initializing Hilo Game");

	// Setup hilo game by using the Guess injected page
	// There is a corresponding setup method in Guess class
	guess.setupGame(seed);

	return guess;
    }

    /**
     * This method is used by the Tapestry "if" component to display or not a
     * message from Hilo Game in the welcome page.
     * 
     * @return true if hilo has been played and successful, false otherwise
     */
    public boolean getLastHiloMessage()
    {
	return (messageFromHilo != null && !("".equals(messageFromHilo)));
    }

}
