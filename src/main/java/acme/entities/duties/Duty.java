package acme.entities.duties;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.roles.Officer;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Duty extends DomainEntity {
	
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
	
	@NotNull
	@Positive
	protected Double workLoad;
	
	@ManyToOne
	@JoinColumn(name = "officerId", referencedColumnName = "id")
	protected Officer officerId;
	

	
	// Derived attributes -----------------------------------------------------

	@Transient
	public Double workload() {

		return ((double) this.executionPeriodEnd.getTime() - this.executionPeriodInit.getTime()) / 3600000;
	}

}
