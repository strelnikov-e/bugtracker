package com.strelnikov.bugtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnore
    private List<Issue> issues = new ArrayList<>();

    @Column
    private String description;

    @Column(columnDefinition = "char")
    private String keyword;

    @Column(name="lead_username")
    private String leadUsername;

    @Column(columnDefinition = "char")
    private String status;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name="start_date")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name="due_date")
    private LocalDate dueDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name="end_date")
    private LocalDate endDate;


    public Project() {

    }

    public Project(String name, String description, String keyword, String leadUsername, String status,
                   LocalDate startDate, LocalDate dueDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.keyword = keyword;
        this.leadUsername = leadUsername;
        this.status = status;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.endDate = endDate;
    }

    public static Project newProject(String name) {
        final var project = new Project();
        project.name = name;
        return project;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        Project that = (Project) obj;
        return this.id != 0L && Objects.equals(this.id, that.id);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLeadUsername() {
        return leadUsername;
    }

    public void setLeadUsername(String leadUsername) {
        this.leadUsername = leadUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", keyword='" + keyword + '\'' +
                ", leadUsername='" + leadUsername + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", endDate=" + endDate +
                '}';
    }
}

