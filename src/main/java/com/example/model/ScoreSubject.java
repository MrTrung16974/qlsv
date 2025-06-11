package com.example.model;

import java.sql.Date;

public class ScoreSubject {
    private String id;
    private double scoreProcess;
    private double scoreFinal;
    private double score_average;
    private Date createAt;
    private Date lassmodified;
    private boolean deleted;
    private Subject subject;
    private Student student;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getScoreProcess() {
        return scoreProcess;
    }

    public void setScoreProcess(double scoreProcess) {
        this.scoreProcess = scoreProcess;
    }

    public double getScoreFinal() {
        return scoreFinal;
    }

    public void setScoreFinal(double scoreFinal) {
        this.scoreFinal = scoreFinal;
    }

    public double getScore_average() {
        return score_average;
    }

    public void setScore_average(double score_average) {
        this.score_average = score_average;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLassmodified() {
        return lassmodified;
    }

    public void setLassmodified(Date lassmodified) {
        this.lassmodified = lassmodified;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ScoreSubject(String id, double scoreProcess, double scoreFinal, double score_average, Date createAt, Date lassmodified, boolean deleted, Subject subject, Student student) {
        this.id = id;
        this.scoreProcess = scoreProcess;
        this.scoreFinal = scoreFinal;
        this.score_average = score_average;
        this.createAt = createAt;
        this.lassmodified = lassmodified;
        this.deleted = deleted;
        this.subject = subject;
        this.student = student;
    }

    public ScoreSubject() {
    }
}
