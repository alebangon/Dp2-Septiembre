package acme.features.manager.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.words.Word;
import acme.features.administrator.spam.AdministratorSpamShowService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerTaskUpdateService implements AbstractUpdateService<Manager, Task> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerTaskRepository repository;
	@Autowired
	protected AdministratorSpamShowService spamService;
	
	// AbstractUpdateService<Administrator, UserAccount> interface -------------


	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		boolean result;
		int taskId;

		Task task;
		

		taskId = request.getModel().getInteger("id");
		task = this.repository.findById(taskId);
		final Manager manager = this.repository.findManagerById(request.getPrincipal().getActiveRoleId());

		result = manager.equals(task.getManagerId());
		return result;
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


		request.unbind(entity, model, "title", "executionPeriodInit", "executionPeriodEnd", "description", "optionalLink", "isPublic", "workLoad");
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;

		Task result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findById(id);

		return result;
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		final String title = entity.getTitle().trim().replace(" ", "").toLowerCase();
		final String desc = entity.getDescription().trim().replace(" ", "").toLowerCase();
 		final List<Word> listSpam = this.spamService.findAll().getSpamWordsList();
 		final String allWords = title+desc;
 		boolean containsSpam = false;
			for(final Word word: listSpam) {
				containsSpam = StringUtils.contains(allWords, word.getSpamWord());
				if(containsSpam) {
					break;
				}
			}
			errors.state(request,!containsSpam, "spam", "acme.validation.spam.task");
			final Date start = request.getModel().getDate("executionPeriodEnd");
			final Date end = request.getModel().getDate("executionPeriodInit");
			final Date now = new Date(System.currentTimeMillis());
			if(start.before(now)||end.before(now)) {
				errors.state(request, !start.before(now), "executionPeriodEnd", "acme.validation.task.date");
				errors.state(request, !end.before(now), "executionPeriodInit", "acme.validation.task.date");
			}
	}
	@Override
	public void update(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;

		entity.setWorkLoad(entity.workload());

		this.repository.save(entity);
	}

}
