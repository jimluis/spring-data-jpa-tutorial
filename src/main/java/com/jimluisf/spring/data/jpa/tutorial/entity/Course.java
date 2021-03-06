package com.jimluisf.spring.data.jpa.tutorial.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course
{
    @Id@SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long courseId;
    private String title;
    private Integer credit;

    @OneToOne( //Just for One to One bi-directional mapping
            mappedBy = "course" // This is only telling that the OneToOne mapping is being done already in the CourseMaterial class by the course attribute
    )
    private CourseMaterial courseMaterial;

    @ManyToOne(
        cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "teacherId"
    )
    private Teacher teacher;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(//we are creating a new table student_course_mapping, which will have two columns course_id and course_id
            name = "student_course_mapping",
            joinColumns = @JoinColumn(
                    name = "course_id", //db
                    referencedColumnName = "courseId" // class property
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "studentId"
            )
    )
    private List<Student> students;

    public void addStudents(Student student)
    {
        if (students == null)
            students = new ArrayList<>();

        students.add(student);
    }
}
