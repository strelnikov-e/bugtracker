package com.strelnikov.bugtracker.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@OneToMany(fetch = LAZY, mappedBy = "user", cascade = PERSIST)
	private List<ProjectRole> projectRoles = new ArrayList<>();

	@OneToMany(fetch = LAZY, mappedBy = "user", cascade = PERSIST)
	private List<IssueRole> issueRoles = new ArrayList<>();

	private String email;

	@Column(columnDefinition = "char")
	private String password;
	
	private boolean enabled;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="company_name")
	private String companyName;


	public User() {
		
	}
	
	public User(String username, String email, String password, boolean enabled, String firstName, String lastName, String companyName) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
	}

	public static User newUser(String username) {
		final var user = new User();
		user.username = username;
		return user;
	}

	public void addIssueRole(Issue issue, IssueRoleType issueRoleType) {
		final var issueRole = new IssueRole();
		issueRole.setIssue(issue);
		issueRole.setType(issueRoleType);
		issueRole.setUser(this);
		issueRoles.add(issueRole);
	}

	public void addProjectRole(Project project, ProjectRoleType projectRoleType) {
		final var projectRole = new ProjectRole();
		projectRole.setProject(project);
		projectRole.setType(projectRoleType);
		projectRole.setUser(this);
		projectRoles.add(projectRole);
	}

	public List<ProjectRole> getProjectRoles() {
		return projectRoles;
	}

	public void setProjectRoles(List<ProjectRole> projectRoles) {
		this.projectRoles = projectRoles;
	}

	public List<IssueRole> getIssueRoles() {
		return issueRoles;
	}

	public void setIssueRoles(List<IssueRole> issueRoles) {
		this.issueRoles = issueRoles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

}
