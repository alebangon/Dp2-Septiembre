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
//	@Temporal(TemporalType.TIME)
	protected String workLoad;
	
	@ManyToOne
	@JoinColumn(name = "officerId", referencedColumnName = "id")
	protected Officer officerId;
	

	
	// Derived attributes -----------------------------------------------------

	public String workload() {
		double hours = ((double) this.executionPeriodEnd.getTime() - this.executionPeriodInit.getTime()) / 3600000;
		final Integer mins = (int) (((hours - Math.floor(hours))*100)*30/50);

		hours = hours - (hours - Math.floor(hours));
		if(hours<100) {
		if(hours<10) {
			if(mins<10) {
				return String.format("0%s:0%s",(int) hours, mins);
			}else {
				return String.format("0%s:%s",(int) hours, mins);
			}
		}else {
			if(mins<10) {
				return String.format("%s:0%s",(int) hours, mins);
			}else {
				return String.format("%s:%s",(int) hours, mins);
			}
		}
		}else {
			return "99:59";
		}
	}
	@Transient
	public Double workloadDouble() {

		return ((double) this.executionPeriodEnd.getTime() - this.executionPeriodInit.getTime()) / 3600000;
	}

}
