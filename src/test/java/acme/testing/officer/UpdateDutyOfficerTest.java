package acme.testing.officer;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class UpdateDutyOfficerTest extends AcmePlannerTest {

	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	

	// Test cases -------------------------------------------------------------


	/**
	 * 
	 * Caso positivo:
	 * En el que un officer actualiza los datos de una tarea, se comprueba que se actualice
	 * de forma correcta según los datos esperados.
	*/

	@ParameterizedTest
	@CsvFileSource(resources = "/Updatedutyofficer/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveUpdatedutyofficer(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		
		this.signIn("officer", "officer");
		super.navigateHome();
		super.clickOnMenu("Officer", "My duties");
		super.clickOnListingRecord(iter);
		super.fill(By.id("title"), title);

		super.clickOnSubmitButton("Update");
		super.checkColumnHasValue(iter, 0, title);
		super.checkColumnHasValue(iter, 1, executionPeriodInit);
		super.checkColumnHasValue(iter, 2, executionPeriodEnd);
		super.checkColumnHasValue(iter, 3, description);
	}

	/**
	 * 
	 * Caso negativo:
	 * En el que un officer actualiza los datos de una tarea, se comprueba que se no se actualice
	 * la tarea ya que se introducen palabras calificadas como spam, por lo cual se espera un error
	 * al enviar los nuevos datos.
	*/


	@ParameterizedTest
    @CsvFileSource(resources = "/UpdateDutyOfficer/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    public void negativeUpdate(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		this.signIn("officer", "officer");
		super.navigateHome();
		super.clickOnMenu("Officer", "My duties");
		super.clickOnListingRecord(iter);
		super.fill(By.id("executionPeriodInit"), executionPeriodInit);

		super.clickOnSubmitButton("Update");
		super.checkErrorsExist();
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


	

	
	
}
