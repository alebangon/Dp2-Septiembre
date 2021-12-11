package acme.features.anonymous.duty;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class AnonymousDutyShowService implements AbstractShowService<Anonymous, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnonymousDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		
		int dutyId;
		Duty duty;
		dutyId = request.getModel().getInteger("id");
		duty = this.repository.findById(dutyId);
		final Date fechaInicio = duty.getExecutionPeriodInit();
		final Date fechaFin = duty.getExecutionPeriodEnd();
		final Date now = new Date(System.currentTimeMillis());
		if(duty.getIsPublic()&&(fechaInicio.after(now)&&fechaFin.after(now))) {
			return true;
		}else{
			return false;
		}

	}

	// AbstractShowService<Anonymous, duty> interface --------------------------

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
