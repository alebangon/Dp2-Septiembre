
package acme.testing.administrator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class DashBoardTest extends AcmePlannerTest {

	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	
	// Test cases -------------------------------------------------------------


	/**
	 * 
	 * Caso positivo:
	 * En el que el dashboard está completo y con datos al contener tareas
	 * Se comprueban que las funcionalidades que hay en el service devuelvan el 
	 * número correcto.
	 */
	//realizamos una consulta a nuestra dashboard y vemos si se corresponden los valores a los que buscamos
	
	@ParameterizedTest
	@CsvFileSource(resources = "/dashboard/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void positiveDashBoardList(final String totalNumberOfPublicDuties, final String totalNumberOfPrivateDuties, final String totalNumberOfFinishedDuties, final String totalNumberOfNonFinishedDuties, final String AverageDutyExecutionPeriods,
		final String DeviationDutyExecutionPeriods, final String MinimumDutyExecutionPeriods, final String MaximumDutyExecutionPeriods, final String MaximumDutyWorkloads, final String MinimumDutyWorkload, final String DeviationDutyWorkloads,
		final String AverageDutyWorkloads) {
		this.signIn("administrator", "administrator");
		super.clickAndGo(By.linkText("Administrator"));
		super.clickAndGo(By.linkText("Dashboard"));
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(1) > td")).getText(), totalNumberOfPublicDuties);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(2) > td")).getText(), totalNumberOfPrivateDuties);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(3) > td")).getText(), totalNumberOfFinishedDuties);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(4) > td")).getText(), totalNumberOfNonFinishedDuties);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(5) > td")).getText(), AverageDutyExecutionPeriods);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(6) > td")).getText(), DeviationDutyExecutionPeriods);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(7) > td")).getText(), MinimumDutyExecutionPeriods);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(8) > td")).getText(), MaximumDutyExecutionPeriods);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(9) > td")).getText(), MaximumDutyWorkloads);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(10) > td")).getText(), MinimumDutyWorkload);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(11) > td")).getText(), DeviationDutyWorkloads);
		Assertions.assertEquals(super.locateOne(By.cssSelector("tr:nth-child(12) > td")).getText(), AverageDutyWorkloads);

	}

	/**
	 * 
	 * Caso Negativo:
	 * 
	 * El usuario Officer no debería ver el dashboard
	 * 
	 */
	//en este caso intentamos realizar una consulta como Officer y nos da un error
	@Test
	@Order(30)
	protected void assertDashboardForOfficerIsForbidden() {
		super.navigateHome();
		this.signIn("officer", "officer");
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/dashboard/show");
		super.checkErrorsExist();

	}

	/**
	 * 
	 * Caso Negativo:
	 * 
	 * El usuario anonimo no debería ver el dashboard
	 * 
	 */
	//en este caso intentamos realizar una consulta como anonymous y nos da un error

	@Test
	@Order(30)
	protected void assertDashboardForAnonymousIsForbidden() {
		super.navigateHome();
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/dashboard/show");
		super.checkErrorsExist();

	}

		/**
	 * 
	 * Caso Negativo:
	 * 
	 * El usuario autenticado no debería ver el dashboard
	 * 
	 */

	@Test
	@Order(30)
	//en este caso intentamos realizar una consulta como usuario y nos da un error

	protected void assertDashboardForAuthenticatedIsForbidden() {
		super.navigateHome();
		this.signUp("username", "password", "name", "surname", "email@example.com");
		this.signIn("username", "password");
		super.driver.get("http://localhost:8080/Acme-Planner/administrator/dashboard/show");
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

	@Override
	protected void signOut() {
		super.navigateHome();
		super.clickAndGo(By.linkText("Sign out"));
	}

	protected void signUp(final String username, final String password, final String name, final String surname, final String email) {
		super.navigateHome();
		super.clickAndGo(By.linkText("Sign up"));
		super.fill(By.id("username"), username);
		super.fill(By.id("password"), password);
		super.fill(By.id("confirmation"), password);
		super.fill(By.id("identity.name"), name);
		super.fill(By.id("identity.surname"), surname);
		super.fill(By.id("identity.email"), email);
		super.clickAndGo(By.id("accept$proxy"));
		super.clickOnSubmitButton("Sign up");
	}

}
