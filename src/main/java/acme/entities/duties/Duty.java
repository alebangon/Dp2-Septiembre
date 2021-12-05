package acme.entities.duties;

import java.sql.Time;
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
	protected Time workLoad;
	
	@ManyToOne
	@JoinColumn(name = "officerId", referencedColumnName = "id")
	protected Officer officerId;
	

	
	// Derived attributes -----------------------------------------------------

	public Time workload() {
		double hours = ((double) this.executionPeriodEnd.getTime() - this.executionPeriodInit.getTime()) / 3600000;
		final Integer mins = (int) (((hours - Math.floor(hours))*100)*30/50);

		hours = hours - (hours - Math.floor(hours));
		System.out.println(mins);
		if(hours<10) {
			if(mins<10) {
				return Time.valueOf(String.format("0%s:0%s:00",(int) hours, mins));
			}else {
				return Time.valueOf(String.format("0%s:%s:00",(int) hours, mins));
			}
		}else {
			if(mins<10) {
				return Time.valueOf(String.format("%s:0%s:00",(int) hours, mins));
			}else {
				return Time.valueOf(String.format("%s:%s:00",(int) hours, mins));
			}
		}
	
	}
	@Transient
	public Double workloadDouble() {

		return ((double) this.executionPeriodEnd.getTime() - this.executionPeriodInit.getTime()) / 3600000;
	}

}
