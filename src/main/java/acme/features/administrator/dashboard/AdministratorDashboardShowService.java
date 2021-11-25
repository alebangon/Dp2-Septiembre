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

		request.unbind(entity, model, "totalNumberOfPublicDuties", "totalNumberOfPrivateDuties", "totalNumberOfFinishedDuties", "totalNumberOfNonFinishedDuties", "averageDutyExecutionPeriods", "deviationDutyExecutionPeriods", "minimumDutyExecutionPeriods", "maximumDutyExecutionPeriods","averageDutyWorloads", "averageDutyWorloads", "deviationDutyWorloads", "minimumDutyWorloads", "maximumDutyWorloads");
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
		Double						averageDutyWorloads;
		Double						deviationDutyWorloads;
		Double						minimumDutyWorloads;
		Double						maximumDutyWorloads;

		final List<Duty> totalDuties = this.repository.allDuties();
		averageDutyWorloads = this.checkValue(this.calculateWorkloadAverage(totalDuties));
		deviationDutyWorloads = this.checkValue(this.calculateWorkloadDeviation(totalDuties));
		totalNumberOfPublicDuties = this.repository.totalNumberOfPublicDuties();
		totalNumberOfPrivateDuties = this.repository.totalNumberOfPrivateDuties();
		totalNumberOfFinishedDuties = this.repository.totalNumberOfFinishedDuties();
		totalNumberOfNonFinishedDuties = this.repository.totalNumberOfNonFinishedDuties();
		averageDutyExecutionPeriods= this.checkValue(this.repository.averageDutyExecutionPeriods());
		deviationDutyExecutionPeriods = this.checkValue(this.repository.deviationDutyExecutionPeriods());
		minimumDutyExecutionPeriods = this.checkValue(this.repository.minimumDutyExecutionPeriods());
		maximumDutyExecutionPeriods = this.checkValue(this.repository.maximumDutyExecutionPeriods());
		minimumDutyWorloads = this.checkValue(this.takeMinimum(totalDuties));
		maximumDutyWorloads = this.checkValue(this.takeMaximum(totalDuties));
		
		result = new Dashboard();
		result.setTotalNumberOfPublicDuties(totalNumberOfPublicDuties);
		result.setTotalNumberOfPrivateDuties(totalNumberOfPrivateDuties);
		result.setTotalNumberOfFinishedDuties(totalNumberOfFinishedDuties);
		result.setTotalNumberOfNonFinishedDuties(totalNumberOfNonFinishedDuties);
		result.setAverageDutyExecutionPeriods(averageDutyExecutionPeriods);
		result.setDeviationDutyExecutionPeriods(deviationDutyExecutionPeriods);
		result.setMaximumDutyExecutionPeriods(maximumDutyExecutionPeriods);
		result.setMinimumDutyExecutionPeriods(minimumDutyExecutionPeriods);
		result.setAverageDutyWorloads(averageDutyWorloads);
		result.setDeviationDutyWorloads(deviationDutyWorloads);
		result.setMinimumDutyWorloads(minimumDutyWorloads);
		result.setMaximumDutyWorloads(maximumDutyWorloads);
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
			
		Double res = Double.MAX_VALUE;
		for(final Duty t: totalDuties) {
			if (res > t.workload()) {
				res = t.workload();
			}
		}
		return res;
		}
	}

	private Double takeMinimum(final List<Duty> totalDuties) {
		if (totalDuties.isEmpty()) {
			return .0;
		}else {
			
		Double res = Double.MIN_VALUE;
		for(final Duty t: totalDuties) {
			if (res < t.workload()) {
				res = t.workload();
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
			totalWorkload += Duty.workload();
		}
		final Double mean = totalWorkload/numberOfDuties;
		for(final Duty Duty: totalDuties){
			deviation += Math.pow(Duty.workload()-mean, 2);

		}
		return Math.sqrt(deviation/numberOfDuties);
	}

	private Double calculateWorkloadAverage(final List<Duty> totalDuties) {
		Double average = .0;
		for (int i = 0; i < totalDuties.size(); i++) {
			average += totalDuties.get(i).workload();
		}
		average /= totalDuties.size();
		return average;
	}
	

}
