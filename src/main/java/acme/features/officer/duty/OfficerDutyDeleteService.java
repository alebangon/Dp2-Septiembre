package acme.features.officer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class OfficerDutyDeleteService implements AbstractDeleteService<Officer, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected OfficerDutyRepository repository;

	// AbstractDeleteService<Employer, Job> interface -------------------------


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean result;
		int dutyId;

		Duty duty;
		

		dutyId = request.getModel().getInteger("id");
		duty = this.repository.findById(dutyId);
		final Officer Officer = this.repository.findOfficerById(request.getPrincipal().getActiveRoleId());

		result = Officer.equals(duty.getOfficerId());
		return result;
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

		request.unbind(entity, model, "title", "executionPeriodInit", "executionPeriodEnd", "description", "optionalLink", "isPublic");
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findById(id);

		return result;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;
		this.repository.delete(entity);
	}

}
