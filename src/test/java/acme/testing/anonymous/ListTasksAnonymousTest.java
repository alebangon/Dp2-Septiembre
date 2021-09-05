package acme.testing.anonymous;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ListTasksAnonymousTest extends AcmePlannerTest {





	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	/**
	 * 
	 * Caso positivo:
	 * En el que un usuario anonimo accede a la lista de tareas y se muestran
	 * las tareas que puede ver este tipo de usuario.
	 * 
	 * No existe caso negativo ya que es un listado.
	*/

	@ParameterizedTest
	@CsvFileSource(resources = "/listTasksAnonymous/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveListTasks(final String title, final String executionPeriodInit, final String executionPeriodEnd,
		final String description, final String optionalLink, final int iter) {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "List tasks");
		super.checkColumnHasValue(iter, 0, title);
		super.checkColumnHasValue(iter, 1, executionPeriodInit);
		super.checkColumnHasValue(iter, 2, executionPeriodEnd);
		super.checkColumnHasValue(iter, 3, description);
		super.navigateHome();
		
	}
	

	// Ancillary methods ------------------------------------------------------

	


}

