
package acme.testing.authenticated;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.SignUpTest;

public class BecomeManagerTest extends SignUpTest {


	@ParameterizedTest
	@CsvFileSource(resources = "/becomeManager/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveBecomeManager(final String company, final String sector) {
		super.signUp("username", "password", "name", "name", "user@mail.com");

		super.signIn("username", "password");
		super.clickOnMenu("Account", "Become a manager");

		super.fillInputBoxIn("company", company); 
		super.fillInputBoxIn("sector", sector);
		super.clickOnSubmitButton("Register");
		super.checkLinkExists("Manager");
		super.signOut();

	}
	
	/*
	 * Tested Feature: usuario se hace manager
	 * Violated constraints: los campos no pueden estar en blanco
	 * Expected results: fallos en formato
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/becomeManager/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void testBecomeManagerNegative(final String company, final String sector) {

		super.signIn("authenticated", "authenticated");
		super.clickOnMenu("Authenticated", "Become a manager");

		super.fillInputBoxIn("company", company);
		super.fillInputBoxIn("sector", sector);
		super.clickOnSubmitButton("Register");
		super.checkErrorsExist();
		super.signOut();

	}

}
