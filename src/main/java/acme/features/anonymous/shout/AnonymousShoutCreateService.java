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

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.shouts.Shout;
import acme.entities.words.Word;
import acme.features.administrator.spam.AdministratorSpamShowService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousShoutCreateService implements AbstractCreateService<Anonymous, Shout> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnonymousShoutRepository repository;
	@Autowired
	protected AdministratorSpamShowService spamService;

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

		request.unbind(entity, model, "author", "text", "info");
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
		final String autorWords = entity.getAuthor().trim().replace(" ", "").toLowerCase();
		final String infoWords = entity.getInfo().trim().replace(" ", "").toLowerCase();
		final String textWords = entity.getText().trim().replace(" ", "").toLowerCase();
 		final List<Word> listSpam = this.spamService.findAll().getSpamWordsList();
 		final Double threshold= this.spamService.findAll().getThreshold();
 		final String allWords = autorWords+infoWords+textWords;
 		Double frecuency=0.0;
			for(final Word word: listSpam) {
				if(StringUtils.contains(autorWords, word.getSpamWord())){
					frecuency=frecuency +1.0;
				}else if(StringUtils.contains(infoWords, word.getSpamWord())){
					frecuency=frecuency +1.0;
				}else if(StringUtils.contains(textWords, word.getSpamWord())){
					frecuency=frecuency +1.0;
				}}
		final Boolean pasaumbral= allWords.length()*(threshold/100.00)<frecuency;
				
		if(pasaumbral==true) {		
			errors.state(request,!pasaumbral, "spam", "anonymous.shout.error.shout-spam");

		}
			}
	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		assert request != null;
		assert entity != null;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);

		
	}

}
