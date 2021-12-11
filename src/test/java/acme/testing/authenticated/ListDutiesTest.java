package acme.testing.authenticated;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class ListDutiesTest extends AcmePlannerTest {





	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	
	// Test cases -------------------------------------------------------------

	/**
	 * 
	 * Caso positivo:
	 * En el que el el usuario autenticado puede ver la lista de las tasks públicas finalizadas.
	 * Al tratarse de un listado, nos encontramos con que únicamente hay caso positivo.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/listDutiesAuthenticated/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveListDuties(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		this.signIn("authenticated", "authenticated");
		super.clickAndGo(By.linkText("Authenticated"));
		super.clickAndGo(By.linkText("Duty list"));
		super.checkColumnHasValue(iter, 0, title);
		super.checkColumnHasValue(iter, 1, executionPeriodInit);
		super.checkColumnHasValue(iter, 2, executionPeriodEnd);
		super.checkColumnHasValue(iter, 3, description);
		super.checkColumnHasValue(iter, 4, optionalLink);
		
		

        super.signOut();

		
		
	}
	
	

	// Ancillary methods ------------------------------------------------------

	
	@Override
	protected void signIn(final String username, final String password) {
		super.navigateHome();
		super.clickAndGo(By.linkText("Sign in"));
		super.fill(By.id("username"), username);
		super.fill(By.id("password"), password);
		super.clickAndGo(By.id("remember$proxy"));
		super.clickOnSubmitButton("Sign in");
		
	}

	@Override
	protected void signOut() {
		super.navigateHome();
		super.clickAndGo(By.linkText("Sign out"));
	}



}

