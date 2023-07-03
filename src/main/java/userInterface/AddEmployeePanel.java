package userInterface;

import model.*;
import controller.*;
import Exceptions.*;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class AddEmployeePanel extends JPanel implements ActionListener {
    private JLabel matriculeLabel, lastNameLabel, firstNameLabel, supervisorLabel, positionLabel, nbChildsLabel, sexLabel, birthdayLabel, isMarriedLabel, phoneNumberLabel;
    private JTextField matriculeField, lastNameField, firstNameField, nbChildsField, phoneNumberField;
    private JCheckBox isMarriedBox;
    private JComboBox<Employee> supervisorComboBox;
    private JComboBox<Fonction> positionComboBox;
    private JRadioButton maleButton, femaleButton, unknownButton;
    private JButton submitButton, cancelButton;
    private ApplicationController controller;
    private JSpinner spinner;
    private SpinnerDateModel model;
    private ArrayList<Employee> supervisorsList;
    private ArrayList<Fonction> positionsList;

    public AddEmployeePanel() {
        try {
            controller = new ApplicationController();
            setLayout(new BorderLayout());

            JPanel formPanel = new JPanel(new GridLayout(0, 2));
            formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            matriculeLabel = new JLabel("Matricule * :");
            matriculeField = new JTextField(20);
            formPanel.add(matriculeLabel);
            formPanel.add(matriculeField);

            lastNameLabel = new JLabel("Nom de famille * : ");
            lastNameField = new JTextField(20);
            formPanel.add(lastNameLabel);
            formPanel.add(lastNameField);

            firstNameLabel = new JLabel("Prénom * : ");
            firstNameField = new JTextField(20);
            formPanel.add(firstNameLabel);
            formPanel.add(firstNameField);

            supervisorLabel = new JLabel("Superviseur * :");
            supervisorComboBox = new JComboBox<>();
            supervisorsList = controller.listEmployees();
            for (Employee employee: supervisorsList) {
                supervisorComboBox.addItem(employee);
            }
            formPanel.add(supervisorLabel);
            formPanel.add(supervisorComboBox);

            positionLabel = new JLabel("Fonction * :");
            positionComboBox = new JComboBox<>();
            positionsList = controller.listFonctions();
            for (Fonction fonction: positionsList) {
                positionComboBox.addItem(fonction);
            }
            formPanel.add(supervisorLabel);
            formPanel.add(supervisorComboBox);

            nbChildsLabel = new JLabel("Nombre d'enfants * :");
            nbChildsField = new JTextField(10);
            formPanel.add(nbChildsLabel);
            formPanel.add(nbChildsField);

            sexLabel = new JLabel("Sexe * :");
            maleButton = new JRadioButton("Mâle");
            femaleButton = new JRadioButton("Femelle");
            unknownButton = new JRadioButton("Inconnu");
            ButtonGroup groupSex = new ButtonGroup();
            groupSex.add(maleButton);
            maleButton.setSelected(true);
            groupSex.add(femaleButton);
            groupSex.add(unknownButton);

            JPanel sexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            formPanel.add(sexLabel);
            formPanel.add(sexPanel);
            sexPanel.add(maleButton);
            sexPanel.add(femaleButton);
            sexPanel.add(unknownButton);

            birthdayLabel = new JLabel("Date d'anniversaire * : ");
            model = new SpinnerDateModel();
            spinner = new JSpinner(model);
            JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd.MM.yyyy");
            DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
            formatter.setAllowsInvalid(false);
            formatter.setOverwriteMode(true);
            formPanel.add(birthdayLabel);
            formPanel.add(spinner);

            isMarriedLabel = new JLabel("Etes-vous marrié ?");
            isMarriedBox = new JCheckBox();
            formPanel.add(isMarriedLabel);
            formPanel.add(isMarriedBox);

            phoneNumberLabel = new JLabel("Numéro de téléphone : ");
            phoneNumberField = new JTextField(20);
            formPanel.add(phoneNumberLabel);
            formPanel.add(phoneNumberField);

            submitButton = new JButton("Ajouter");
            cancelButton = new JButton("Annuler");
            submitButton.addActionListener(this);
            cancelButton.addActionListener(this);

            formPanel.add(submitButton);
            formPanel.add(cancelButton);
            add(formPanel, BorderLayout.CENTER);

        } catch (ListFonctionsException | ListEmployeesException | SingletonConnexionException e) {
            e.printStackTrace();
        }

    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Ajouter")){
            String matricule = matriculeField.getText();
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            Employee supervisor = (Employee) supervisorComboBox.getSelectedItem();
            Fonction position = (Fonction) positionComboBox.getSelectedItem();
            String nbChilds = nbChildsField.getText();
            Gender sex;
            if (maleButton.isSelected()){
                sex = Gender.M;
            } else if (femaleButton.isSelected()){
                sex = Gender.F;
            } else {
                sex = Gender.X;
            }
            Date birthday = model.getDate();
            Boolean isMarried = isMarriedBox.isSelected();
            String phoneNumber = phoneNumberField.getText();
            if (phoneNumberField.getText().isEmpty()){
                phoneNumber = "";
            }

            // TODO Ajout animal
        }
    }
}
