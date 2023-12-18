package se.iths;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentID", nullable = false)
    private Integer id;

    @Column(name = "studentName", length = 50)
    private String studentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentCourseID")
    private Course studentCourseID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Course getStudentCourseID() {
        return studentCourseID;
    }

    public void setStudentCourseID(Course studentCourseID) {
        this.studentCourseID = studentCourseID;
    }

}