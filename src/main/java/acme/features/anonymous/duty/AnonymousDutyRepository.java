package acme.features.anonymous.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousDutyRepository extends AbstractRepository {
	@Query("select d from Duty d where d.isPublic=true AND d.executionPeriodInit > now()")
	Collection<Duty> findMany();
	
	@Query("select d from Duty d where d.id=?1")
	Duty findById(int id);

}
