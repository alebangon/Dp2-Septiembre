package acme.features.officer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class OfficerDutyShowService implements AbstractShowService<Officer, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected OfficerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean result = false;
		int dutyId;

		Duty duty;
		dutyId = request.getModel().getInteger("id");
		duty = this.repository.findById(dutyId);

		final Officer officer = this.repository.findOfficerById(request.getPrincipal().getActiveRoleId());
		result = officer.equals(duty.getOfficerId());



		
		return result;
	}

	// AbstractShowService<Anonymous, Job> interface --------------------------

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "executionPeriodInit", "executionPeriodEnd", "description", "optionalLink", "isPublic", "workLoad");
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

}
