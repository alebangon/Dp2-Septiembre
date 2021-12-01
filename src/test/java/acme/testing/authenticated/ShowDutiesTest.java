package acme.testing.authenticated;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class ShowDutiesTest extends AcmePlannerTest {





	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	/**
	 * 
	 * Caso positivo:
	 * En el que el usuario autenticado puede ver los detalles de las tasks de la lista de tareas
	 * públicas finalizadas.
	 * Al tratarse de uns vista de detalles, nos encontramos con que únicamente hay caso positivo.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/showDutiesAuthenticated/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveShowDuty(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		this.signIn("authenticated", "authenticated");
		super.clickAndGo(By.linkText("Authenticated"));
		super.clickAndGo(By.linkText("Duty list"));
		super.clickOnListingRecord(iter);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("executionPeriodInit", executionPeriodInit);
		super.checkInputBoxHasValue("executionPeriodEnd", executionPeriodEnd);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("optionalLink", optionalLink);
		
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
