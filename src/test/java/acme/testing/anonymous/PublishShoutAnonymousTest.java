package acme.testing.anonymous;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class PublishShoutAnonymousTest extends AcmePlannerTest {

	// Internal state ---------------------------------------------------------

	// Lifecycle management ---------------------------------------------------

	
	// Test cases -------------------------------------------------------------
	/**
	 * 
	 * Caso positivo:
	 *
	 * Se crea un shout haciendo que sus atributos cumplan las restricciones y validadores pertinentes
	 * Se comprueba la correcta creación del shout.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/publishShout/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positivePublishShout(final String author, final String text, final String optionalLink) {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "Shout!");
		super.fill(By.id("author"), author);
		super.fill(By.id("text"), text);
		super.fill(By.id("optionalLink"), optionalLink);
		super.clickOnSubmitButton("Shout!");
		super.clickOnMenu("Anonymous", "List shouts");
		super.checkColumnHasValue(2, 1, author);
        super.checkColumnHasValue(2, 2, text);
        super.clickOnMenu("Anonymous", "List shouts");
		
	}
	
	/**
	 * 
	 * Caso negativo:
	 *
	 * Se crea un shout haciendo uno de sus atributos incumpla la restricción de NotNull.
	 * Se comprueba que no se crea dicho shout y aparece el error esperado.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/publishShout/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativePublishShout(final String author, final String text, final String optionalLink) {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "Shout!");
		super.fill(By.id("author"), author);
		super.fill(By.id("text"), text);
		super.fill(By.id("optionalLink"), optionalLink);
		super.clickOnSubmitButton("Shout!");
		super.checkErrorsExist();
		
	}

	// Ancillary methods ------------------------------------------------------

	




}

