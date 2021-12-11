/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalNumberOfPublicDuties", "totalNumberOfPrivateDuties", "totalNumberOfFinishedDuties", "totalNumberOfNonFinishedDuties", "averageDutyExecutionPeriods", "deviationDutyExecutionPeriods", "minimumDutyExecutionPeriods", "maximumDutyExecutionPeriods","averageDutyWorkloads", "averageDutyWorkloads", "deviationDutyWorkloads", "minimumDutyWorkloads", "maximumDutyWorkloads");
		}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result;
		Integer 					totalNumberOfPublicDuties;
		Integer						totalNumberOfPrivateDuties;
		Integer						totalNumberOfFinishedDuties;
		Integer						totalNumberOfNonFinishedDuties;
		Double						averageDutyExecutionPeriods;
		Double						deviationDutyExecutionPeriods;
		Double						minimumDutyExecutionPeriods;
		Double						maximumDutyExecutionPeriods;
		Double						averageDutyWorkloads;
		Double						deviationDutyWorkloads;
		Double						minimumDutyWorkloads;
		Double						maximumDutyWorkloads;

		final List<Duty> totalDuties = this.repository.allDuties();
		averageDutyWorkloads = this.checkValue(this.calculateWorkloadAverage(totalDuties));
		deviationDutyWorkloads = this.checkValue(this.calculateWorkloadDeviation(totalDuties));
		totalNumberOfPublicDuties = this.repository.totalNumberOfPublicDuties();
		totalNumberOfPrivateDuties = this.repository.totalNumberOfPrivateDuties();
		totalNumberOfFinishedDuties = this.repository.totalNumberOfFinishedDuties();
		totalNumberOfNonFinishedDuties = this.repository.totalNumberOfNonFinishedDuties();
		averageDutyExecutionPeriods= this.checkValue(this.repository.averageDutyExecutionPeriods());
		deviationDutyExecutionPeriods = this.checkValue(this.repository.deviationDutyExecutionPeriods());
		minimumDutyExecutionPeriods = this.checkValue(this.repository.minimumDutyExecutionPeriods());
		maximumDutyExecutionPeriods = this.checkValue(this.repository.maximumDutyExecutionPeriods());
		minimumDutyWorkloads = this.checkValue(this.takeMinimum(totalDuties));
		maximumDutyWorkloads = this.checkValue(this.takeMaximum(totalDuties));
		
		result = new Dashboard();
		result.setTotalNumberOfPublicDuties(totalNumberOfPublicDuties);
		result.setTotalNumberOfPrivateDuties(totalNumberOfPrivateDuties);
		result.setTotalNumberOfFinishedDuties(totalNumberOfFinishedDuties);
		result.setTotalNumberOfNonFinishedDuties(totalNumberOfNonFinishedDuties);
		result.setAverageDutyExecutionPeriods(averageDutyExecutionPeriods);
		result.setDeviationDutyExecutionPeriods(deviationDutyExecutionPeriods);
		result.setMaximumDutyExecutionPeriods(maximumDutyExecutionPeriods);
		result.setMinimumDutyExecutionPeriods(minimumDutyExecutionPeriods);
		result.setAverageDutyWorkloads(averageDutyWorkloads);
		result.setDeviationDutyWorkloads(deviationDutyWorkloads);
		result.setMinimumDutyWorkloads(minimumDutyWorkloads);
		result.setMaximumDutyWorkloads(maximumDutyWorkloads);
		return result;
	}


	private Double checkValue(final Double value) {
		Double res = value;
		if (value == null || value.isNaN()) {
			res = .0;
		}
		return res;
	}

	private Double takeMaximum(final List<Duty> totalDuties) {
		if (totalDuties.isEmpty()) {
			return .0;
		}else {
			
		Double res = 0.;
		for(final Duty t: totalDuties) {
			if (res < t.workloadDouble()) {
				res = t.workloadDouble();
			}
		}
		return res;
		}
	}

	private Double takeMinimum(final List<Duty> totalDuties) {
		if (totalDuties.isEmpty()) {
			return .0;
		}else {
			
		Double res = totalDuties.get(0).workloadDouble();
		for(final Duty t: totalDuties) {
			if (res > t.workloadDouble()) {
				res = t.workloadDouble();
			}
		}
		return res;
		}
	}

	private Double calculateWorkloadDeviation(final List<Duty> totalDuties) {
		Double totalWorkload = .0;
		Double deviation = .0;
		final Integer numberOfDuties = totalDuties.size();
		for(final Duty Duty: totalDuties){
			totalWorkload += Duty.workloadDouble();
		}
		final Double mean = totalWorkload/numberOfDuties;
		for(final Duty Duty: totalDuties){
			deviation += Math.pow(Duty.workloadDouble()-mean, 2);

		}
		return Math.sqrt(deviation/numberOfDuties);
	}

	private Double calculateWorkloadAverage(final List<Duty> totalDuties) {
		Double average = .0;
		for (int i = 0; i < totalDuties.size(); i++) {
			average += totalDuties.get(i).workloadDouble();
		}
		average /= totalDuties.size();
		return average;
	}
	

}
