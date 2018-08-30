package com.ao.entity;

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
public class BaseOperator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int creator;

	private int modifier;

	@Temporal(TemporalType.TIMESTAMP)
	private Date moditime;

	private String name;

	public BaseOperator() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getCreator() {
		return this.creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public int getModifier() {
		return this.modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public Date getModitime() {
		return this.moditime;
	}

	public void setModitime(Date moditime) {
		this.moditime = moditime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}