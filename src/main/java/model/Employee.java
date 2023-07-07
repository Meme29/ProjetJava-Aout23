package model;

import java.util.Date;

public class Employee {
    private String matricule;
    private String lastName;
    private String firstName;
    private String supervisor;
    private String position;

    private int nbChilds;
    private char sex;
    private Date birthday;
    private Boolean isMArried;
    private String phoneNumber;

    public Employee(String matricule, String lastName, String firstName, String supervisor, String position, int nbChilds, char sex, Date birthday, Boolean isMArried, String phoneNumber) {
        this.matricule = matricule;
        this.lastName = lastName;
        this.firstName = firstName;
        this.supervisor = supervisor;
        this.position = position;
        this.nbChilds = nbChilds;
        setSex(sex);
        this.birthday = birthday;
        this.isMArried = isMArried;
        this.phoneNumber = phoneNumber;
    }

    public Employee(String matricule, String lastName, String firstName, String supervisor, String position, int nbChilds, char sex, Date birthday, Boolean isMArried) {
        this.matricule = matricule;
        this.lastName = lastName;
        this.firstName = firstName;
        this.supervisor = supervisor;
        this.position = position;
        this.nbChilds = nbChilds;
        setSex(sex);
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

    public char getSex() {
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

    public void setSex(char sex) {
        if (sex == 'F' | sex == 'M' | sex == 'X') {
            this.sex = sex;
        } else {
            this.sex = 'X';
        }
    }

    @Override
    public String toString(){
        return lastName + " " + firstName.charAt(0) + ".  (" + matricule + " )";
    }
}
