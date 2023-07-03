package userInterface;

import Exceptions.ListEmployeesException;
import Exceptions.SingletonConnexionException;
import controller.ApplicationController;
import model.Employee;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EmployeeListingPanel extends JPanel {
    private ApplicationController controller;

    private ArrayList<Employee> employeesList;

    private JTable jTable;
    private JScrollPane scrollPanel;
    public EmployeeListingPanel(){

        try {
            controller = new ApplicationController();
            setLayout(new BorderLayout());
            employeesList = controller.listEmployees();
            EmployeeListingTableModel model = new EmployeeListingTableModel(employeesList);
            jTable = new JTable(model);
            jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPanel = new JScrollPane (jTable);
            add(scrollPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        }catch (ListEmployeesException | SingletonConnexionException exception){
            JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }
}
