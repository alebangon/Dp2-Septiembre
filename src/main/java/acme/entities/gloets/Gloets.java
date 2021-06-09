package acme.entities.gloets;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Gloets extends DomainEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;
	
	@Column(unique=true)
	@NotBlank
	//@Pattern(regexp = "^yy/mmdd/\\w{2,4}$" ) 
	protected String			bow;
	
	@NotNull
	protected Date 				deadline;
	
	@Valid
	protected Money				budget;
	
	@NotNull
	protected Boolean			important;
	
	
}
