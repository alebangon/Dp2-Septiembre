package acme.features.authenticated.duty;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedDutyRepository extends AbstractRepository {
	@Query("select d from Duty d where d.isPublic=true AND d.executionPeriodInit < now()")
	Collection<Duty> findPublicDuties();
	
	@Query("select d from Duty d where d.id=?1")
	Duty findById(int id);
	
	@Query("select d from Duty d")
    Collection<Duty> findMany();
	
	void save(Duty duty);

	

}