package acme.features.authenticated.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedTaskShowService implements AbstractShowService<Authenticated, Task> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTaskRepository repository;


	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		
		int taskId;
		Task task;
		taskId = request.getModel().getInteger("id");
		task = this.repository.findById(taskId);
		final Date fechaInicio = task.getExecutionPeriodInit();
		final Date fechaFin = task.getExecutionPeriodEnd();
		final Date now = new Date(System.currentTimeMillis());
		if(task.getIsPublic()&&(fechaInicio.after(now)&&fechaFin.after(now))) {
			return true;
		}else{
			return false;
		}
	}

	// AbstractShowService<Authenticated, Job> interface --------------------------

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "executionPeriodInit", "executionPeriodEnd", "description", "optionalLink", "isPublic");
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

}