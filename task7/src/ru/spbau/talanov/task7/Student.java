package ru.spbau.talanov.task7;

/**
 * A student POJO.
 */
@SuppressWarnings("UnusedDeclaration")
public final class Student {

    private String firstName;
    private String lastName;
    private int age;
    private double averageGrade;

    /**
     * Constructs a new Student object with all properties set to their default values.
     */
    public Student() {
    }

    /**
     * Constructs a new Student object with properties set to passed values.
     *
     * @param name     student's name.
     * @param surname  student's surname.
     * @param age      student's age.
     * @param avgGrade student's average grade.
     */
    public Student(String name, String surname, int age, double avgGrade) {
        firstName = name;
        lastName = surname;
        this.age = age;
        averageGrade = avgGrade;
    }

    /**
     * Get student's name.
     *
     * @return name or null if it was not set.
     */
    public String getName() {
        return firstName;
    }

    /**
     * Set student's name.
     *
     * @param name new name.
     */
    public void setName(String name) {
        firstName = name;
    }

    /**
     * Get student's surname.
     *
     * @return surname or null if it was not set.
     */
    public String getSurname() {
        return lastName;
    }

    /**
     * Set student's surname.
     *
     * @param surname new surname.
     */
    public void setSurname(String surname) {
        lastName = surname;
    }

    /**
     * Get student's age.
     *
     * @return age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Set student's age.
     *
     * @param age new age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Get student's average grade.
     *
     * @return average grade.
     */
    public double getAvgGrade() {
        return averageGrade;
    }

    /**
     * Set student's average grade.
     *
     * @param avgGrade new average grade.
     */
    public void setAvgGrade(double avgGrade) {
        averageGrade = avgGrade;
    }

}
