package acme.features.administrator.spam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.spam.Spam;
import acme.entities.words.Word;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Administrator;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

 @Service
public class AdministratorSpamUpdateService implements AbstractUpdateService<Administrator, Spam> {
	
	@Autowired
	protected AdministratorSpamRepository spamRepo;
	
	@Override
	public boolean authorise(final Request<Spam> request) {
		assert request != null;
		boolean result= false;
		if(request.getPrincipal().getActiveRole().equals(Administrator.class)) {
			result= true;
			
		}

		return result;
	}

	
	
	@Override
	public Spam findOne(final Request<Spam> request) {
		assert request != null;

		Collection<Spam> result;

		result = this.spamRepo.searchOne();

		return result.iterator().next();
	
	}
	
	@Override
	public void bind(final Request<Spam> request, final Spam entity, final Errors errors) {
			assert request!=null;
			assert entity!= null;
			assert errors!=null;
			
			request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Spam> request, final Spam entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "threshold", "spamWordsList");
	}
	
	@Override
	public void validate(final Request<Spam> request, final Spam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		final List<Word> spamWordsList = entity.getSpamWordsList();
		if(spamWordsList.isEmpty()) {
			errors.state(request, false, "spamWordsList", "administrator.word.error.empty");

		}
		final Double Threshold = entity.getThreshold();
		if(Threshold.equals(0)||Threshold.equals(null)	) {
			errors.state(request, false, "threshold", "administrator.threshold.error.empty");

		}
	
			
	}

	@Override
	public void update(final Request<Spam> request,final Spam entity) {
		assert request != null;
		assert entity != null;
		int i = 0;
		final String[] s = request.getModel().getAttribute("lista").toString().trim().split(",");
		final List<Word> l = new ArrayList<Word>();
		while(i< s.length) {
			l.add(new Word(s[i].trim()));
			i++;
		}
		entity.setSpamWordsList(l);
		this.spamRepo.save(entity);	
		}
	@Override
	public void onSuccess(final Request<Spam> request, final Response<Spam> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
