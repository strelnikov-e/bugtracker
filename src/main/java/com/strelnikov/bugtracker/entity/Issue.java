package com.strelnikov.bugtracker.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="issues")
public class Issue {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int status;
	
	private String name;
	
	private String description;
	
	@Column(name="start_date")
	private Date startDate;

	@Column(name="target_date")
	private Date targetDate;
	
	@Column(name="project_id")
	private Long projectId;

	
	public Issue() {
		
	}


	public Issue(Long id, int status, String name, String description, Date startDate, Date targetDate,
			Long projectId) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.targetDate = targetDate;
		this.projectId = projectId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getTargetDate() {
		return targetDate;
	}


	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}


	public Long getProjectId() {
		return projectId;
	}


	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
}
