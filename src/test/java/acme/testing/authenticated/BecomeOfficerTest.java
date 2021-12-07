
package acme.testing.authenticated;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class BecomeOfficerTest extends AcmePlannerTest {

	/*
	 * Tested Feature: usuario se da de alta y se hace manager con éxito
	 * Expected results: cuenta authenticated creada y hecha manager sin errores
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/becomeOfficer/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveBecomeOfficer(final String company, final String sector) {
		super.navigateHome();
		super.clickAndGo(By.linkText("Sign up"));
		super.fill(By.id("username"), "username");
		super.fill(By.id("password"), "password");
		super.fill(By.id("confirmation"), "password");
		super.fill(By.id("identity.name"), "name");
		super.fill(By.id("identity.surname"), "surname");
		super.fill(By.id("identity.email"), "email@gmail.com");
		super.clickAndGo(By.id("accept$proxy"));
		super.clickOnSubmitButton("Sign up");
		
		super.signIn("username", "password");
		super.clickOnMenu("Account", "Become a officer");

		super.fillInputBoxIn("company", company); 
		super.fillInputBoxIn("sector", sector);
		super.clickOnSubmitButton("Register");
		super.checkLinkExists("Officer");
		super.signOut();

	}
	
	/*
	 * Tested Feature: usuario se hace manager sin éxito
	 * Violated constraints: los campos no pueden estar en blanco
	 * Expected results: fallos en formato
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/becomeOfficer/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void testBecomeOfficerNegative(final String company, final String sector) {

		super.signIn("authenticated", "authenticated");
		super.clickOnMenu("Account", "Become a officer");

		super.fillInputBoxIn("company", company);
		super.fillInputBoxIn("sector", sector);
		super.clickOnSubmitButton("Register");
		super.checkErrorsExist();
		super.signOut();

	}

}
