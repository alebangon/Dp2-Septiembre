package acme.entities.tasks;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import acme.framework.entities.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task extends DomainEntity {
	
	// Serialisation identifier -----------------------------------------------
	
	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------------------
	
	@NotBlank
	@Length(min=1, max=80)
	protected String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date executionPeriodInit;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date executionPeriodEnd;
	
	@NotBlank
	@Length(min=1, max=500)
	protected String description;
	
	@URL
	protected String optionalLink;
	
	@NotNull
	protected Boolean isPublic;
	
	@ManyToOne
	@JoinColumn(name = "managerId", referencedColumnName = "id")
	protected Manager managerId;
	

	
	// Derived attributes -----------------------------------------------------

	@Digits(fraction=2, integer=10)
	public Double workload(){
		final Long b = Math.abs(this.executionPeriodEnd.getTime()-this.executionPeriodInit.getTime());
		final Long a = TimeUnit.MINUTES.convert(b, TimeUnit.MILLISECONDS);
		return a.doubleValue()/60.0;
	}

}
