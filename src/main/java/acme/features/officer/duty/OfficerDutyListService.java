/*
 * ManagerShoutListService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class OfficerDutyListService implements AbstractListService<Officer, Duty> {


	// Internal state ---------------------------------------------------------

	@Autowired
	protected OfficerDutyRepository repository;


	// AbstractListService<Administrator, Task> interface --------------
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		
		Collection<Duty> collection;
		final int id = request.getPrincipal().getActiveRoleId();
		collection = this.repository.findDutiesByOfficer(id);
		final Officer officer = this.repository.findOfficerById(request.getPrincipal().getActiveRoleId());
		boolean res = officer!=null;

		for (final Duty d: collection) {
			if (d.getOfficerId().getId()!=officer.getId()&&res)
				res=false;
			}

		return res;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "executionPeriodInit", "executionPeriodEnd", "description", "optionalLink", "isPublic", "workLoad");
	}

	@Override
	public Collection<Duty> findMany(final Request<Duty> request) {
		assert request != null;

		Collection<Duty> result;
		final int id = request.getPrincipal().getActiveRoleId();
		result = this.repository.findDutiesByOfficer(id);


		return result;
	}

}
