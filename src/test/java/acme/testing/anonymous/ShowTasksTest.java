package acme.testing.anonymous;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class ShowTasksTest extends AcmePlannerTest {





	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	/**
	 * 
	 * Caso positivo:
	 * En el que un usuario anonimo accede a los detalles de una tarea y se muestran
	 * los detalles de la misma correspondientes a lo que debería mostrar
	 * según la base de datos.
	*/

	@ParameterizedTest
	@CsvFileSource(resources = "/showTasksAnonymous/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveShowTask(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "List tasks");
		super.clickOnListingRecord(iter);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("executionPeriodInit", executionPeriodInit);
		super.checkInputBoxHasValue("executionPeriodEnd", executionPeriodEnd);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("optionalLink", optionalLink);
		super.navigateHome();
		
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
