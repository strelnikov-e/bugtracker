package com.strelnikov.bugtracker.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
    private String keyword;

    @Column(name="lead_username")
    private String leadUsername;

    @Column
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

