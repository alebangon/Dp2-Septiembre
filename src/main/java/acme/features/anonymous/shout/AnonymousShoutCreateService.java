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
		result.setOptionalLink("http://example.org");

		return result;
	}

	@Override
	public void validate(final Request<Shout> request, final Shout entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final String[] autorWords = entity.getAuthor().split(" ");
		final String[] infoWords = entity.getOptionalLink().split(" ");
		final String[] textWords = entity.getText().split(" ");
 		final List<Word> listSpam = this.spamService.findAll().getSpamWordsList();
 		final Double threshold= this.spamService.findAll().getThreshold();
 		Double frecuency=0.0;
		Double frequencyAutor = 0.0;
		Double frequencyInfo = 0.0;
		Double frequencyText = 0.0;
 		Boolean pasaumbral= false;
			for(final Word word: listSpam) {
				for (final String st : autorWords) {
					if(st.toLowerCase().contains(word.getSpamWord().toLowerCase())){
						frecuency++;
						frequencyAutor++;
						}
					}
				for (final String st : infoWords) {
					if(st.toLowerCase().contains(word.getSpamWord().toLowerCase())){
						frecuency++;
						frequencyInfo++;
						}
					}
				for (final String st : textWords) {
					if(st.toLowerCase().contains(word.getSpamWord().toLowerCase())){
						frecuency++;
						frequencyText++;
						}
				}
				}
	        pasaumbral= (autorWords.length+infoWords.length+textWords.length)*(threshold/100.00)<frecuency;
			if(frequencyAutor>0)
				errors.state(request, !pasaumbral, "author", "acme.validation.shout.spam");
			if(frequencyInfo>0)
				errors.state(request, !pasaumbral, "info", "acme.validation.shout.spam");
			if(frequencyText>0)
				errors.state(request, !pasaumbral, "text", "acme.validation.shout.spam");

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
