/*
 * TaskCreateService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.officer.duty;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.entities.words.Word;
import acme.features.administrator.spam.AdministratorSpamShowService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class OfficerDutyCreateService implements AbstractCreateService<Officer, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected OfficerDutyRepository			repository;
	@Autowired
	protected AdministratorSpamShowService	spamService;

	// AbstractCreateService<Administrator, Task> interface --------------


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "executionPeriodInit", "executionPeriodEnd", "description", "optionalLink", "isPublic", "workLoad");
	}

	@Override
	public Duty instantiate(final Request<Duty> request) {
		assert request != null;

		Duty result;

		result = new Duty();
		result.setDescription("descripcion");
		result.setExecutionPeriodEnd(new Date(System.currentTimeMillis()));
		result.setExecutionPeriodInit(new Date(System.currentTimeMillis()));
		result.setTitle("Duty");
		result.setIsPublic(true);
		result.setOptionalLink("https://www.google.com");
		result.setWorkLoad("10:00:00");

		return result;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		final String[] descWords = entity.getDescription().split(" ");
		final String[] titleWords = entity.getTitle().split(" ");
		final List<Word> listSpam = this.spamService.findAll().getSpamWordsList();
		final Double threshold = this.spamService.findAll().getThreshold();
		Double frequency = 0.0;
		Double frequencyTitle = 0.0;
		Double frequencyDesc = 0.0;
		Boolean pasaumbral = false;
		for (final Word word : listSpam) {
			for (final String st : descWords) {
				if (st.toLowerCase().contains(word.getSpamWord().toLowerCase())) {
					frequency++;
					frequencyDesc++;
				}
			}
			for (final String st : titleWords) {
				if (st.toLowerCase().contains(word.getSpamWord().toLowerCase())) {
					frequency++;
					frequencyTitle++;
				}
			}

		}

		pasaumbral = (descWords.length + titleWords.length) * (threshold / 100.00) < frequency;
		if(frequencyTitle>0)
			errors.state(request, !pasaumbral, "title", "acme.validation.duty.spam");
		if(frequencyDesc>0)
			errors.state(request, !pasaumbral, "description", "acme.validation.duty.spam");
		if (request.getModel().getDate("executionPeriodEnd") != null && request.getModel().getDate("executionPeriodInit") != null) {
			final Date start = request.getModel().getDate("executionPeriodInit");
			final Date end = request.getModel().getDate("executionPeriodEnd");
			final Date now = new Date(System.currentTimeMillis());
			if (start.before(now) || end.before(now)) {
				errors.state(request, !start.before(now), "executionPeriodEnd", "acme.validation.duty.date");
				errors.state(request, !end.before(now), "executionPeriodInit", "acme.validation.duty.date");
				}
			if(start.after(end)) {
				errors.state(request, !start.after(end), "executionPeriodInit", "acme.validation.duty.workload");
			}
		}
	}

	@Override
	public void create(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;
		entity.setWorkLoad(entity.workload());
		final Integer officerId = request.getPrincipal().getActiveRoleId();
		entity.setOfficerId(this.repository.findOfficerById(officerId));

		this.repository.save(entity);
	}

}
