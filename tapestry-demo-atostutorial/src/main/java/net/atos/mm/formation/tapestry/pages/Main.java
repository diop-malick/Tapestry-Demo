package net.atos.mm.formation.tapestry.pages;

import java.text.SimpleDateFormat;

import net.atos.mm.formation.tapestry.data.Portfolio;
import net.atos.mm.formation.tapestry.data.User;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Main {

	private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	/** Show the loop grid */
	private static final String LOOP = "loop";

	/** Show the Grid component */
	private static final String GRID = "grid";

	/**
	 * Used to have a reference on the authenticated user
	 */
	@SessionState
	private User loggedUser;

	private boolean loggedUserExists;

	/**
	 * Used to store a reference on the simple grid view
	 */
	@Inject
	private Block simpleList;

	/**
	 * Used to store a reference on the advanced grid component view
	 */
	@Inject
	private Block advancedList;

	/**
	 * Used to store the grid display in user session
	 */
	@Persist
	private String mode;

	/**
	 * Used to store the row index number
	 */
	private int index = 0;

	/**
	 * Used to store the currentPortfolio
	 */
	private Portfolio currentPortfolio;

	/**
	 * Used to modify the display of "bookingDate"
	 */
	private SimpleDateFormat dateFormat;

	@Inject
	private Messages messages;

	/**
	 * Used to store
	 */
	private String rowClass;

	/**
	 * Used to verify if the user is logged on
	 * 
	 * @return the Index page if user doesn't exist in session, null otherwise
	 */
	@OnEvent("activate")
	public Object assertUserExists() {

		// Create date formatter
		if (messages.contains("dateFormat")) {
			try {
				dateFormat = new SimpleDateFormat(messages.get("dateFormat"));
			} catch (Exception ex) {
				dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			}
		} else {
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		}

		// Verify if user has logged in
		if (!loggedUserExists) {
			return Login.class;
		}

		return null;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User user) {
		loggedUser = user;
	}

	public Portfolio getCurrentPortfolio() {
		return currentPortfolio;
	}

	public void setCurrentPortfolio(Portfolio aCurrentPortfolio) {
		this.currentPortfolio = aCurrentPortfolio;
	}

	/**
	 * This method is used for the zebra effect on the grid
	 * 
	 * @return "tbl1" or "tlb2" in function of "index modulo 2"
	 */
	public String getRowClass() {
		// Implement here the choice of CSS class used to display a row

		// Choose class for the current index
		String result = "tbl1";
		if (index % 2 != 0) {
			result = "tbl2";
		}

		// Increment index if the grid component is used
		if (GRID.equals(this.mode)) {
			index++;
		}

		return result;

	}

	public void setRowClass(String rowClass) {
		this.rowClass = rowClass;
	}

	/**
	 * This method is used by the delegate component to get the block to display
	 * 
	 */
	public Block getPorfoliosGrid() {

		// Check for the selected mode and return the appropriate block
		if (GRID.equals(this.mode)) {
			return this.advancedList;
		} else {
			return simpleList;
		}

	}

	/**
	 * This method is used to setup the advanced view mode
	 * 
	 */
	@OnEvent(value = "action", component = "advanced")
	public void selectAdvancedMode() {
		this.mode = GRID;
	}

	/**
	 * This method is used to setup the simple view mode
	 * 
	 */
	@OnEvent(value = "action", component = "simple")
	public void selectSimpleMode() {
		this.mode = LOOP;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
