package se.iths;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseID", nullable = false)
    private Integer id;

    @Column(name = "courseName", length = 50, unique = true)
    private String courseName;

    @Column(name = "courseEvaluation")
    private Integer courseEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseProgramID")
    private Program courseProgramID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseTeacherID")
    private Teacher courseTeacherID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseEvaluation() {
        return courseEvaluation;
    }

    public void setCourseEvaluation(Integer courseEvaluation) {
        this.courseEvaluation = courseEvaluation;
    }

    public Program getCourseProgramID() {
        return courseProgramID;
    }

    public void setCourseProgramID(Program courseProgramID) {
        this.courseProgramID = courseProgramID;
    }

    public Teacher getCourseTeacherID() {
        return courseTeacherID;
    }

    public void setCourseTeacherID(Teacher courseTeacherID) {
        this.courseTeacherID = courseTeacherID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}