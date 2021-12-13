package acme.entities.X;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class X extends DomainEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@Column(unique=true)
	@Pattern(regexp = "^[a-zA-Z]{5}-[0-9]{2}/[0-9]{2}/[0-9]{2}$")
	protected String X1;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				X2;
	@Valid
	protected Money			X3;

	@NotNull
	protected Boolean			isImportant;

	
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}