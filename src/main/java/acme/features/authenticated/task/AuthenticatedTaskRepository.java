package acme.features.authenticated.task;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedTaskRepository extends AbstractRepository {
	@Query("select t from Task t where t.isPublic=true AND t.executionPeriodInit < now()")
	Collection<Task> findPublicTasks();
	
	@Query("select t from Task t where t.id=?1")
	Task findById(int id);
	
	@Query("select t from Task t")
    Collection<Task> findMany();
	
	void save(Task task);

}