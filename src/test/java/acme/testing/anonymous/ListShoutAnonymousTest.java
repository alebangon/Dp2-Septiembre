package acme.testing.anonymous;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ListShoutAnonymousTest extends AcmePlannerTest {

	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------
	/**
	 * 
	 * Caso positivo:
	 *
	 * Se comprueba que la lista donde se muestran los shouts se despliega acorde a lo esperado.
	 * 
	 * No existe caso negativo ya que es un listado.
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/listShout/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveListShout(final int recordIndex, final String moment, final String author, final String optionalLink) {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "List shouts");
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, optionalLink);
		super.navigateHome();
		
		
	}
	

	// Ancillary methods ------------------------------------------------------



}

