package acme.features.anonymous.gloets;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.gloets.Gloets;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousGloetsRepository extends AbstractRepository {

	@Query("select g from Gloets g")
	Gloets findOne();
	@Query("select g from Gloets g where g.budget.currency=?1")
	Gloets findByCurrency(String currency);
}


