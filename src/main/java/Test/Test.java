package Test;

import model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class EmployeeTest {
    EmployeeTest() {
    }

    @Test
    void setSex() {
        Date date = new Date("10/10/10");
        Employee employee = new Employee("e1", "j", "j", "e2", "f2", 2, 'X', date, true);

        // Test valide
        employee.setSex('F');
        Assertions.assertEquals('F', employee.getSex());

        // Test avec valeur fausse
        employee.setSex('D');
        Assertions.assertEquals('X', employee.getSex());
    }
}