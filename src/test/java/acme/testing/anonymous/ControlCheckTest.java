package acme.testing.anonymous;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class ControlCheckTest  extends AcmePlannerTest {

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
	@CsvFileSource(resources = "/controlCheck/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positivePublishShout(final Integer recordIndex,final String author, final String text, final String optionalLink, final String X1, final String X2, final String X3) {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "Shout!");
		super.fill(By.id("author"), author);
		super.fill(By.id("text"), text);
		super.fill(By.id("optionalLink"), optionalLink);
		super.fill(By.id("X.X1"), X1);
		super.fill(By.id("X.X2"), X2);
		super.fill(By.id("X.X3"), X3);
		super.clickOnSubmitButton("Shout!");
		super.clickOnMenu("Anonymous", "List shouts");
		super.checkColumnHasValue(recordIndex, 1, author);
        super.checkColumnHasValue(recordIndex, 2, text);
        super.checkColumnHasValue(recordIndex, 3, optionalLink);
        super.checkColumnHasValue(recordIndex, 4, X1);
        super.checkColumnHasValue(recordIndex, 5, X2);
        super.checkColumnHasValue(recordIndex, 6, X3);
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
	@CsvFileSource(resources = "/controlCheck/negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativePublishShout(final String author, final String text, final String optionalLink, final String X1, final String X2, final String X3) {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "Shout!");
		super.fill(By.id("author"), author);
		super.fill(By.id("text"), text);
		super.fill(By.id("optionalLink"), optionalLink);
		super.fill(By.id("X.X1"), X1);
		super.fill(By.id("X.X2"), X2);
		super.fill(By.id("X.X3"), X3);
		super.clickOnSubmitButton("Shout!");
		super.checkErrorsExist();
		
	}

	// Ancillary methods ------------------------------------------------------

	




}
