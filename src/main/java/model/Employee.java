package model;

import java.util.Date;

public class Employee {
    private String matricule;
    private String lastName;
    private String firstName;
    private String supervisor;
    private String position;

    private int nbChilds;
    private Gender sex;
    private Date birthday;
    private Boolean isMArried;
    private String phoneNumber;

    public Employee(String matricule, String lastName, String firstName, String supervisor, String position, int nbChilds, Gender sex, Date birthday, Boolean isMArried, String phoneNumber) {
        this.matricule = matricule;
        this.lastName = lastName;
        this.firstName = firstName;
        this.supervisor = supervisor;
        this.position = position;
        this.nbChilds = nbChilds;
        this.sex = sex;
        this.birthday = birthday;
        this.isMArried = isMArried;
        this.phoneNumber = phoneNumber;
    }

    public Employee(String matricule, String lastName, String firstName, String supervisor, String position, int nbChilds, Gender sex, Date birthday, Boolean isMArried) {
        this.matricule = matricule;
        this.lastName = lastName;
        this.firstName = firstName;
        this.supervisor = supervisor;
        this.position = position;
        this.nbChilds = nbChilds;
        this.sex = sex;
        this.birthday = birthday;
        this.isMArried = isMArried;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getPosition() {
        return position;
    }

    public int getNbChilds() {
        return nbChilds;
    }

    public Gender getSex() {
        return sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Boolean getMArried() {
        return isMArried;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
        // TODO setGender
    }

    @Override
    public String toString(){
        return lastName + " " + firstName.charAt(0) + ".  (" + matricule + " )";
    }
}
