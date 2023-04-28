package com.strelnikov.bugtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="issues")
public class Issue {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private String assignee;

	private String reporter;

	private String status;

	private String severity;

	private boolean reproducible;

	@Column(name="start_date")
	private LocalDate startDate;

	@Column(name="due_date")
	private LocalDate dueDate;

	@Column(name="closed_date")
	private LocalDate closedDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="project_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Project project;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "issues-tags",
			joinColumns = { @JoinColumn(name = "issue_id") },
			inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private Set<Tag> tags = new HashSet<>();


	public Issue() {
		
	}

	public Issue(String name, String description, String assignee, String reporter,
				 LocalDate startDate, LocalDate dueDate, LocalDate closedDate) {
		this.name = name;
		this.description = description;
		this.assignee = assignee;
		this.reporter = reporter;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.closedDate = closedDate;
	}

	public Issue(String name, String description, String assignee,
				 String reporter, String status, String severity, boolean reproducible,
				 LocalDate startDate, LocalDate dueDate, LocalDate closedDate) {
		this.name = name;
		this.description = description;
		this.assignee = assignee;
		this.reporter = reporter;
		this.status = status;
		this.severity = severity;
		this.reproducible = reproducible;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.closedDate = closedDate;
	}

	public void removeTag(Long tagId) {
		Tag tag = this.tags.stream()
				.filter(t -> Objects.equals(t.getId(), tagId))
				.findFirst()
				.orElse(null);
		if (tag != null) {
			this.tags.remove(tag);
			tag.getIssues().remove(this);
		}
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
		tag.getIssues().add(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public boolean isReproducible() {
		return reproducible;
	}

	public void setReproducible(boolean reproducible) {
		this.reproducible = reproducible;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Issue{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", assignee='" + assignee + '\'' +
				", reporter='" + reporter + '\'' +
				", status='" + status + '\'' +
				", severity='" + severity + '\'' +
				", reproducible=" + reproducible +
				", startDate=" + startDate +
				", dueDate=" + dueDate +
				", closedDate=" + closedDate +
				", project=" + project +
				'}';
	}
}
