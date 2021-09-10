package acme.entities.XXXX;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class XXXX extends DomainEntity {
	// Serialisation identifier -----------------------------------------------
	
	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------------------

	@NotNull
	@Column(unique= true)
	protected String aTRIBUTO1;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date aTRIBUTO2;
	
	@Valid
	protected Money	aTRIBUTO3;
	
	@NotNull
	protected Boolean aTRIBUTO4;

}
