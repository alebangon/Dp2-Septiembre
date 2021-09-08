package acme.testing.anonymous;

import java.time.LocalDateTime;

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
	public void positivePublishShout(final String author, final String text, final String info, final String amount, final String currency) {
		super.navigateHome();
		final String fecha = this.fecha();
		super.clickOnMenu("Anonymous", "Shout!");
		super.fill(By.id("author"), author);
		super.fill(By.id("text"), text);
		super.fill(By.id("info"), info);
		super.fill(By.id("XXXX.aTRIBUTO3"), currency+amount);
		super.fill(By.id("XXXX.aTRIBUTO2"), fecha);
		
		super.clickOnSubmitButton("Shout!");
		super.clickOnMenu("Anonymous", "List shouts");
		super.checkColumnHasValue(2, 1, author);
        super.checkColumnHasValue(2, 2, text);
        super.checkColumnHasValue(2, 4, fecha);
        super.checkColumnHasValue(2, 5, amount);
        super.checkColumnHasValue(2, 6, currency);
        super.checkColumnHasValue(2, 7, "false");
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
	public void negativePublishShout(final String author, final String text, final String info) {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "Shout!");
		super.fill(By.id("author"), author);
		super.fill(By.id("text"), text);
		super.fill(By.id("info"), info);
		super.clickOnSubmitButton("Shout!");
		super.checkErrorsExist();
		
	}

	// Ancillary methods ------------------------------------------------------

	public String fecha() {
		final LocalDateTime fecha = LocalDateTime.now().plusDays(8);
		String month = String.valueOf(fecha.getMonthValue());
		String day = String.valueOf(fecha.getDayOfMonth());
		String hour = String.valueOf(fecha.getHour());
		String minutes = String.valueOf(fecha.getMinute());
		if(fecha.getMonthValue()<10) {
			month = "0"+fecha.getMonthValue();
		}if(fecha.getDayOfMonth()<10) {
			day = "0" + fecha.getDayOfMonth();
		}
		if(fecha.getHour()<10) {
			hour = "0" + hour;
		}if(fecha.getMinute()<10) {
			minutes = "0" + minutes;
		}
		final String res = fecha.getYear()+"/" + month+"/" + day+ " "+hour+":"+minutes;
		return res;
	}




}

