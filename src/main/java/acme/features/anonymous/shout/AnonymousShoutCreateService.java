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
import acme.features.anonymous.gloets.AnonymousGloetsRepository;
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
	
	@Autowired
	protected AnonymousGloetsRepository gloetsRepository;

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

		request.unbind(entity, model, "author", "text", "info", "gloetsId.bow", "gloetsId.budget", "gloetsId.deadline", "gloetsId.important");
	}

	@Override
	public Shout instantiate(final Request<Shout> request) {
		assert request != null;
		Date deadline;
		Shout result;
		

		deadline = new Date(System.currentTimeMillis() - 1);

		result = new Shout();
		result.setMoment(deadline);

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
 		final String allWords = autorWords+infoWords+textWords;
 		boolean containsSpam = false;
			for(final Word word: listSpam) {
				containsSpam = StringUtils.contains(allWords, word.getSpamWord());
				if(containsSpam) {
					break;
				}
			}
			errors.state(request,!containsSpam, "spam", "acme.validation.spam");
			
			final boolean validCurrency = (entity.getGloetsId().getBudget().getCurrency().equals("EUR"))||(entity.getGloetsId().getBudget().getCurrency().equals("USD"));
            errors.state(request,validCurrency, "currency", "acme.validation.currency");
			
			final Date now = new Date(System.currentTimeMillis()+ 7 * 24 * 3600 * 1000);
	        final boolean isDateValid= entity.getGloetsId().getDeadline().after(now);
	        errors.state(request, isDateValid, "deadline", "acme.validation.date");

	}

	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		
		assert request != null;
        assert entity != null;
        Date moment;
        
        if(entity.getGloetsId().getImportant()==null) {
        	entity.getGloetsId().setImportant(false);
        }

        moment = new Date(System.currentTimeMillis() - 1);
        entity.setMoment(moment);

        this.gloetsRepository.save(entity.getGloetsId());
        this.repository.save(entity);
		
	}

}
