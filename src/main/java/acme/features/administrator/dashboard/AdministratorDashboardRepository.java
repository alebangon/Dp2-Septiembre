/*
 * AdministratorDashboardRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {
	@Query ("select count(d) from Duty d where d.isPublic = true")
	Integer totalNumberOfPublicDuties();
	@Query ("select count(d) from Duty d where d.isPublic = false")
	Integer totalNumberOfPrivateDuties();
	@Query ("select count(d) from Duty d where d.executionPeriodEnd < CURRENT_TIMESTAMP")
	Integer totalNumberOfFinishedDuties();
	@Query ("select count(d) from Duty d where d.executionPeriodEnd > CURRENT_TIMESTAMP")
	Integer totalNumberOfNonFinishedDuties();
	@Query("select avg(datediff(d.executionPeriodEnd,d.executionPeriodInit)) from Duty d")
	Double averageDutyExecutionPeriods();
	@Query ("select stddev(datediff(d.executionPeriodInit, d.executionPeriodEnd)) from Duty d")
	Double deviationDutyExecutionPeriods();

	@Query ("select min(datediff(d.executionPeriodEnd,d.executionPeriodInit)) from Duty d")
	Double minimumDutyExecutionPeriods();

	@Query ("select max(datediff(d.executionPeriodEnd,d.executionPeriodInit)) from Duty d")
	Double maximumDutyExecutionPeriods();
	@Query ("select d from Duty d")
	List<Duty> allDuties();
}
