/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltonb.uni.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elton.ballhysa
 */
public class Transcript {
    
    private int studentId;
    private String name;
    private String surname;
    private List<Entry> grades;
    
    public Transcript() {
        grades = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Entry> getGrades() {
        return grades;
    }

    public void setGrades(List<Entry> grades) {
        this.grades = grades;
    }
    
    
    
    public static class Entry {
        private String semester;
        private String courseCode;
        private String courseTitle;
        private String instrutor;
        private String letterGrade;

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public String getInstrutor() {
            return instrutor;
        }

        public void setInstrutor(String instrutor) {
            this.instrutor = instrutor;
        }

        public String getLetterGrade() {
            return letterGrade;
        }

        public void setLetterGrade(String letterGrade) {
            this.letterGrade = letterGrade;
        }
        
        
    }
    
}
