package userInterface;

import Exceptions.DeleteEmployeeException;
import Exceptions.ListEmployeesException;
import Exceptions.SingletonConnexionException;
import controller.ApplicationController;
import model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteEmployeePanel extends JPanel implements ActionListener {
    private JComboBox<Employee> comboBox;
    private JLabel matricule;
    private ArrayList<Employee> allEmployee;
    private JPanel panel;
    private ApplicationController controller;
    private JButton button;

    public DeleteEmployeePanel(){

        try {
            controller = new ApplicationController();
            allEmployee = new ArrayList<>();
            allEmployee = controller.listEmployees();
            setLayout(new BorderLayout());
            panel = new JPanel(new GridLayout(0,4));
            matricule = new JLabel("Matricule de l'employé : ");
            panel.add(matricule);

            comboBox = new JComboBox<>();
            for (Employee employee: allEmployee) {
                comboBox.addItem(employee);
            }
            panel.add(comboBox);
            button = new JButton("Supprimer");
            button.addActionListener(this);
            panel.add(button);
            add(panel,BorderLayout.NORTH);
        }catch (ListEmployeesException | SingletonConnexionException exception){
            JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {

        Employee employeeChosen = (Employee) comboBox.getSelectedItem();
        int dialogResult = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet employé ? Cela entraînera la suppression de toutes les données le concernant.", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION){
            try {
                controller.deleteEmployee(employeeChosen.getMatricule());
                comboBox.removeItemAt(comboBox.getSelectedIndex());
                JOptionPane.showMessageDialog (null, "L'employé a bien été supprimé", "Réussite", JOptionPane.INFORMATION_MESSAGE);
            }catch (DeleteEmployeeException exception){
                JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
