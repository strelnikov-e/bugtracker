package com.strelnikov.bugtracker.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="projects")
public class Project {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int status;

    @Column(name="assignee_email")
    private String assigneeEmail;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name="start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name="target_date")
    private Date targetDate;

    public Project() {

    }

    public Project(String name, String description, int status, String leadUsername, Date startDate, Date targetDate) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.assigneeEmail = leadUsername;
        this.startDate = startDate;
        this.targetDate = targetDate;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigneeEmail(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
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

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", leadUsername='" + assigneeEmail + '\'' +
                ", startDate=" + startDate +
                ", targetDate=" + targetDate +
                '}';
    }
}
