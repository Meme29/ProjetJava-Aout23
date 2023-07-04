package Exceptions;

public class EmployeeIsExisting extends Exception{
    public EmployeeIsExisting(String message) {
        super(message);
    }
}
