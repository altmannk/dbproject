package se.iths;

import jakarta.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gradeID", nullable = false)
    private Integer id;

    @Column(name = "gradeValue", length = 2)
    private String gradeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gradeStudentID")
    private Student gradeStudentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gradeCourseID")
    private Course gradeCourseID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Student getGradeStudentID() {
        return gradeStudentID;
    }

    public void setGradeStudentID(Student gradeStudentID) {
        this.gradeStudentID = gradeStudentID;
    }

    public Course getGradeCourseID() {
        return gradeCourseID;
    }

    public void setGradeCourseID(Course gradeCourseID) {
        this.gradeCourseID = gradeCourseID;
    }

}