/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author zheln
 */
public class Student {

    private int num;
    private String firstname;
    private String lastname;

    public Student(int num, String firstname, String lastname) {
        this.num = num;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getNum() {
        return num;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
