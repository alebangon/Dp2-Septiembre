package acme.features.officer.duty;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.entities.words.Word;
import acme.features.administrator.spam.AdministratorSpamShowService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class OfficerDutyUpdateService implements AbstractUpdateService<Officer, Duty> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected OfficerDutyRepository repository;
	@Autowired
	protected AdministratorSpamShowService spamService;
	
	// AbstractUpdateService<Administrator, UserAccount> interface -------------


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean result;
		int dutyId;

		Duty duty;
		

		dutyId = request.getModel().getInteger("id");
		duty = this.repository.findById(dutyId);
		final Officer officer = this.repository.findOfficerById(request.getPrincipal().getActiveRoleId());

		result = officer.equals(duty.getOfficerId());
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

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
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
			errors.state(request,!containsSpam, "spam", "acme.validation.spam.duty");
			final Date start = request.getModel().getDate("executionPeriodEnd");
			final Date end = request.getModel().getDate("executionPeriodInit");
			final Date now = new Date(System.currentTimeMillis());
			if(start.before(now)||end.before(now)) {
				errors.state(request, !start.before(now), "executionPeriodEnd", "acme.validation.duty.date");
				errors.state(request, !end.before(now), "executionPeriodInit", "acme.validation.duty.date");
			}
	}
	@Override
	public void update(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		entity.setWorkLoad(entity.workload());

		this.repository.save(entity);
	}

}
