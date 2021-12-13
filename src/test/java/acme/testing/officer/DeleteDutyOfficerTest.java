package acme.testing.officer;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class DeleteDutyOfficerTest extends AcmePlannerTest {

	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------


		/**
	 * 
	 * Caso positivo:
	 * En el que se elimina una tarea con exito.
	*/

	@ParameterizedTest
	@CsvFileSource(resources = "/DeleteDutyOfficer/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveDeleteDutyOfficer(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		this.signIn("officer", "officer");
		super.navigateHome();
		super.clickOnMenu("Officer", "My duties");
		super.clickOnListingRecord(iter);
		super.clickOnSubmitButton("Delete");
		super.checkNotPanicExists();

	}

		/**
	 * 
	 * Caso negativo:
	 * En el que un usuario que no es manager intenta ver las tareas para ese manager
	*/

	@ParameterizedTest
    @CsvFileSource(resources = "/DeleteDutyOfficer/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    public void negativeUpdate(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		super.navigateHome();
		super.navigate("/Acme-Endeavours/officer/duty/list", "");
		super.checkPanicExists();
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
