package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the base_operator database table.
 * 
 */
@Entity
@Table(name="base_operator")
@NamedQuery(name="BaseOperator.findAll", query="SELECT b FROM BaseOperator b")
public class BaseOperator extends BaseBisEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	private String name;

	public BaseOperator() {
	}

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}