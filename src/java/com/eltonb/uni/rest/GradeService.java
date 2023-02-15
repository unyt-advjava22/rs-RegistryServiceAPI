/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eltonb.uni.rest;

import com.eltonb.uni.db.entities.*;
import com.eltonb.uni.model.GradeData;
import com.eltonb.uni.model.Transcript;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 *
 * @author elton.ballhysa
 */
@Path("grades")
public class GradeService {

    @PersistenceUnit
    private EntityManagerFactory emf;

    
    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void submitGrade(GradeData gd) {
        EntityManager em = emf.createEntityManager();
        StudentCoursePK scpk = new StudentCoursePK(gd.getStudentId(), gd.getCourseCode(), gd.getSemesterCode());
        StudentCourse sc = em.find(StudentCourse.class, scpk);
        if (sc == null) {
            sc = new StudentCourse(scpk);
        }
        sc.setInstructor(gd.getInstructor());
        sc.setFinalGrade(gd.getFinalGrade());
        sc.setLetterGrade(letterGradeFor(gd.getFinalGrade()));
        
        em.getTransaction().begin();
        em.persist(sc);
        em.getTransaction().commit();
    }

    private String letterGradeFor(double finalGrade) {
        if (finalGrade >= 95) return "A";
        if (finalGrade >= 90) return "A-";
        if (finalGrade >= 87) return "B+";
        if (finalGrade >= 83) return "B";
        if (finalGrade >= 80) return "B-";
        if (finalGrade >= 77) return "C+";
        if (finalGrade >= 73) return "C";
        if (finalGrade >= 70) return "C-";
        if (finalGrade >= 67) return "D+";
        if (finalGrade >= 63) return "D";
        if (finalGrade >= 60) return "D-";
        return "F";
    }
    
    private Transcript.Entry toTranscriptEntry(StudentCourse studentCourse) {
        Transcript.Entry e = new Transcript.Entry();
        e.setCourseCode(studentCourse.getCourse().getCode());
        e.setCourseTitle(studentCourse.getCourse().getTitle());
        e.setInstrutor(studentCourse.getInstructor());
        e.setLetterGrade(studentCourse.getLetterGrade());
        e.setSemester(studentCourse.getSemester().getCode());
        return e;
    }
    
    @GET
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Transcript retrieveTranscript(@PathParam("studentId") int studentId) {
        EntityManager em = emf.createEntityManager();
        Student student = em.find(Student.class, studentId);
        List<Transcript.Entry> gradeEntries = em.createNamedQuery("StudentCourses.findByStudentId", StudentCourse.class)
                .setParameter("studentId", studentId)
                .getResultStream()
                .sorted(Comparator.comparing(sc->sc.getSemester().getCode()))
                .map(this::toTranscriptEntry)
                .collect(Collectors.toList());
        Transcript transcript = new Transcript();
        transcript.setStudentId(student.getId());
        transcript.setName(student.getName());
        transcript.setSurname(student.getSurname());
        transcript.setGrades(gradeEntries);
        return transcript;
    }
    
}
