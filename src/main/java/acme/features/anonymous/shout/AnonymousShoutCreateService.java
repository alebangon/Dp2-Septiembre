/*
 * AnonymousShoutCreateService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.anonymous.shout;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.shouts.Shout;
import acme.entities.words.Word;
import acme.features.administrator.spam.AdministratorSpamShowService;
import acme.features.anonymous.XXXX.AnonymousXXXXRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousShoutCreateService implements AbstractCreateService<Anonymous, Shout> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnonymousShoutRepository		repository;
	@Autowired
	protected AdministratorSpamShowService	spamService;
	@Autowired
	protected AnonymousXXXXRepository		XXXXRepo;

	// AbstractCreateService<Administrator, Shout> interface --------------


	@Override
	public boolean authorise(final Request<Shout> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Shout> request, final Shout entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Shout> request, final Shout entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "author", "text", "info", "XXXX.aTRIBUTO1", "XXXX.aTRIBUTO2", "XXXX.aTRIBUTO3.currency", "XXXX.aTRIBUTO3.amount", "XXXX.aTRIBUTO4");
	}

	@Override
	public Shout instantiate(final Request<Shout> request) {
		assert request != null;

		Shout result;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new Shout();
		result.setAuthor("John Doe");
		result.setText("Lorem ipsum!");
		result.setMoment(moment);
		result.setInfo("http://example.org");

		return result;
	}

	@Override
	public void validate(final Request<Shout> request, final Shout entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		final String[] autorWords = entity.getAuthor().split(" ");
		final String[] infoWords = entity.getInfo().split(" ");
		final String[] textWords = entity.getText().split(" ");
		final List<Word> listSpam = this.spamService.findAll().getSpamWordsList();
		final Double threshold = this.spamService.findAll().getThreshold();
		Double frecuency = 0.0;
		Boolean pasaumbral = false;
		for (final Word word : listSpam) {
			for (final String st : autorWords) {
				if (st.contains(word.getSpamWord())) {
					frecuency = 1.0 + frecuency;
				}
			}
			for (final String st : infoWords) {
				if (st.contains(word.getSpamWord())) {
					frecuency = 1.0 + frecuency;
				}
			}
			for (final String st : textWords) {
				if (st.contains(word.getSpamWord())) {
					frecuency = 1.0 + frecuency;
				}
			}
		}

		pasaumbral = (autorWords.length + infoWords.length + textWords.length) * (threshold / 100.00) > frecuency;
		errors.state(request, pasaumbral, "spam", "acme.validation.spam");

		final Date oneWeek = new Date(System.currentTimeMillis() + 7 * 24 * 3600 * 1000);
		final boolean isDateValid = entity.getXXXX().getATRIBUTO2().after(oneWeek);
		errors.state(request, isDateValid, "ATRIBUTO2", "acme.validation.date");
	}

	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		assert request != null;
		assert entity != null;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		final Integer id = entity.getId();
		String aTRIBUTO1;
		final LocalDate date = LocalDate.now();
		final int year = date.getYear() - 2000;
		String month = String.valueOf(date.getMonthValue());
		if (month.length() == 1) {
			month = "0" + month;
		}
		String day = String.valueOf(date.getDayOfMonth());
		if (day.length() == 1) {
			day = "0" + day;
		}

		aTRIBUTO1 = this.getCharForNumber(id + (int) (Math.random() * (100))) + this.getCharForNumber(id + (int) (Math.random() * (100))) + this.getCharForNumber(id + (int) (Math.random() * (100))) +"-"+ String.valueOf(year) + month + day
			+"-"+ this.getCharForNumber(id + (int) (Math.random() * (100))) + this.getCharForNumber(id + (int) (Math.random() * (100)));

		entity.getXXXX().setATRIBUTO1(aTRIBUTO1);
		this.XXXXRepo.save(entity.getXXXX());

		this.repository.save(entity);

	}

	private String getCharForNumber(final int i) {
		final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

		return Character.toString(alphabet[i % 25]);
	}
}
