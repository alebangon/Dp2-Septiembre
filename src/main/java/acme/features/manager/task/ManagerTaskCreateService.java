/*
 ManagerTaskCreateService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.entities.words.Word;
import acme.features.administrator.spam.AdministratorSpamShowService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerTaskRepository repository;
	@Autowired
	protected AdministratorSpamShowService spamService;

	// AbstractCreateService<Administrator, Task> interface --------------

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title",  "executionPeriodInit",
			"executionPeriodEnd","description","optionalLink", "isPublic");
	}

	@Override
	public Task instantiate(final Request<Task> request) {
		assert request != null;

		Task result;


		result = new Task();
		result.setDescription("descripcion");
		result.setExecutionPeriodEnd(new Date(System.currentTimeMillis()));
		result.setExecutionPeriodInit(new Date(System.currentTimeMillis()));
		result.setTitle("Task");
		result.setIsPublic(true);
		result.setOptionalLink("https://www.google.com");
		

		return result;
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		
		final String[] descWords = entity.getDescription().split(" ");
		final String[] titleWords = entity.getTitle().split(" ");
 		final List<Word> listSpam = this.spamService.findAll().getSpamWordsList();
 		final Double threshold= this.spamService.findAll().getThreshold();
 		Double frecuency=0.0;
 		Boolean pasaumbral= false;
			for(final Word word: listSpam) {
				for (final String st : descWords) {
					if(st.contains(word.getSpamWord())){
						frecuency=1.0+frecuency;
						}
					}
				for (final String st : titleWords) {
					if(st.contains(word.getSpamWord())){
						frecuency=1.0+frecuency;
						}
					}

				}
			
			pasaumbral= (descWords.length+titleWords.length)*(threshold/100.00)>frecuency;
			errors.state(request,  pasaumbral, "spam", "acme.validation.spam");
		
		
		final Date start = request.getModel().getDate("executionPeriodEnd");
		final Date end = request.getModel().getDate("executionPeriodInit");
		final Date now = new Date(System.currentTimeMillis());
		if(start.before(now)||end.before(now)) {
			errors.state(request, !start.before(now), "executionPeriodEnd", "acme.validation.task.date");
			errors.state(request, !end.before(now), "executionPeriodInit", "acme.validation.task.date");
		}
	}

	@Override
	public void create(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;

		final Integer managerId= request.getPrincipal().getActiveRoleId();
		entity.setManagerId(this.repository.findManagerById(managerId));
		
		
		this.repository.save(entity);
	}

}
