package acme.features.officer.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface OfficerDutyRepository extends AbstractRepository {
	@Query("select d from Duty d where d.officerId.id=?1")
	Collection<Duty> findDutiesByOfficer(int id);
	
	@Query("select d from Duty d where d.id=?1")
	Duty findById(int id);
	
	@Query("select o from Officer o where o.id=?1")
	Officer findOfficerById(int id);

	
	

}
