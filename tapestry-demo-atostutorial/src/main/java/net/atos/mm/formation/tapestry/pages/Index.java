package net.atos.mm.formation.tapestry.pages;

import net.atos.mm.formation.tapestry.data.User;
import net.atos.mm.formation.tapestry.services.IUserManager;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Index
{

    /**
     * Used to store a UserManager instance on the page instance
     */
    @Inject
    private IUserManager manager;

    /**
     * This variable is used to set a reference on the authenticated after
     * verify process
     */
    @SessionState
    private User loggedUser;

    /**
     * Use to store the current user login
     */
    private String login;

    /**
     * Use to store the password of the current user
     */
    private String password;

    /**
     * Used to have a reference on the form component
     */
    @Component(id = "verifyForm")
    private Form verifyForm;

    @OnEvent("activate")
    public void activateManager()
    {

    }

    public String getLogin()
    {
	return login;
    }

    @Validate("required")
    public void setLogin(String login)
    {
	this.login = login;
    }

    public String getPassword()
    {
	return password;
    }

    @Validate("required")
    public void setPassword(String password)
    {
	this.password = password;
    }

    /**
     * This method implements the login process. Verify if the user exists and
     * if his password is correct.
     * 
     * @return
     */
    @OnEvent(value = "success", component = "verifyForm")
    public Object verifyUser()
    {

	String errorMsg = "Wrong password or user doesn't exist...";

	// Verify if user exists
	User ttlUser = manager.getUserByLogin(login);
	if (ttlUser != null)
	{
	    // Verify User Password
	    if (password.compareTo(ttlUser.getPassword()) == 0)
	    {
		loggedUser = ttlUser;
		return Main.class;
	    } else
	    {
		verifyForm.recordError(errorMsg);
	    }
	} else
	{
	    verifyForm.recordError(errorMsg);
	}

	return this;

    }

}