package dto;

import java.util.Objects;

public class StudentDto {
    private int matricule;
    private String firstName;
    private String lastName;

    public StudentDto(int matricule, String firstName, String lastName) {
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return matricule == that.matricule && firstName.equals(that.firstName) && lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricule, firstName, lastName);
    }
}
