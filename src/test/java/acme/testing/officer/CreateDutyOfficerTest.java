package acme.testing.officer;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class CreateDutyOfficerTest extends AcmePlannerTest {





	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	/**
	 * 
	 * Caso positivo:
	 * En el que el manager puede crear una tarea nueva sin problemas.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/createDutiesOfficer/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveCreateDuties(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		this.signIn("officer", "officer");
		super.clickAndGo(By.linkText("Officer"));
		super.clickAndGo(By.linkText("Create Duty"));
		super.fill(By.id("title"), title);
		super.fill(By.id("executionPeriodInit"), executionPeriodInit);
		super.fill(By.id("executionPeriodEnd"), executionPeriodEnd);
		super.fill(By.id("description"), description);
		super.fill(By.id("optionalLink"), optionalLink);
		super.clickOnSubmitButton("Create duty");
		
	
		
		
	}
	
	/**
	 * 
	 * Caso negativo:
	 * En el que el manager no puede crear una tarea ya finalizada y
	 * le salta la validación del service.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/createDutiesOfficer/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeCreateDuties(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		this.signIn("officer", "officer");
		super.clickAndGo(By.linkText("Officer"));
		super.clickAndGo(By.linkText("Create Duty"));
		super.fill(By.id("title"), title);
		super.fill(By.id("executionPeriodInit"), executionPeriodInit);
		super.fill(By.id("executionPeriodEnd"), executionPeriodEnd);
		super.fill(By.id("description"), description);
		super.fill(By.id("optionalLink"), optionalLink);
		super.clickOnSubmitButton("Create duty");
		super.checkErrorsExist();
		super.clickOnMenu("Officer", "My duties");

		
		
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

