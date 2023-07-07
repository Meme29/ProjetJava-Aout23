package userInterface;

import Exceptions.*;
import controller.ApplicationController;
import model.Employee;
import model.Fonction;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class ModifyEmployeePanel extends JPanel implements ActionListener {
    private JComboBox<Employee> comboBox;
    private ArrayList<Employee> allEmployees;
    private JPanel panel;
    private String currentMatricule;

    private JLabel matriculeLabel, lastNameLabel, firstNameLabel, supervisorLabel, positionLabel, nbChildsLabel, sexLabel, birthdayLabel, isMarriedLabel, phoneNumberLabel;
    private JTextField lastNameField, firstNameField, nbChildsField, phoneNumberField;
    private JCheckBox isMarriedBox;
    private JComboBox<Employee> supervisorComboBox;
    private JComboBox<Fonction> positionComboBox;
    private JRadioButton maleButton, femaleButton, unknownButton;
    private JButton submitButton, cancelButton, submitChoiceButton;
    private ApplicationController controller;
    private JSpinner spinner;
    private SpinnerDateModel model;
    private ArrayList<Employee> supervisorsList;
    private ArrayList<Fonction> positionsList;

    public ModifyEmployeePanel() {
        try {
            controller = new ApplicationController();
            allEmployees = new ArrayList<>();
            allEmployees = controller.listEmployees();
            setLayout(new BorderLayout());
            panel = new JPanel(new GridLayout(0,4));
            matriculeLabel = new JLabel("Employé : ");
            panel.add(matriculeLabel);

            comboBox = new JComboBox<>();
            for (Employee employee: allEmployees) {
                comboBox.addItem(employee);
            }
            panel.add(comboBox);
            submitChoiceButton = new JButton("Valider choix");
            submitChoiceButton.addActionListener(this);
            panel.add(submitChoiceButton);
            add(panel,BorderLayout.NORTH);
        } catch (ListEmployeesException | SingletonConnexionException exception){
            JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }

    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Valider choix")){
            Employee employeeChosen = (Employee) comboBox.getSelectedItem();
            currentMatricule = employeeChosen.getMatricule();
            panel.removeAll();

            setLayout(new BorderLayout());

            try {
                JPanel formPanel = new JPanel(new GridLayout(0, 2));
                formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                lastNameLabel = new JLabel("Nom de famille * : ");
                lastNameField = new JTextField(20);
                lastNameField.setText(employeeChosen.getLastName());
                formPanel.add(lastNameLabel);
                formPanel.add(lastNameField);

                firstNameLabel = new JLabel("Prénom * : ");
                firstNameField = new JTextField(20);
                firstNameField.setText(employeeChosen.getFirstName());
                formPanel.add(firstNameLabel);
                formPanel.add(firstNameField);

                supervisorLabel = new JLabel("Superviseur * :");
                supervisorComboBox = new JComboBox<>();
                supervisorsList = controller.listEmployees();
                for (Employee employee: supervisorsList) {
                    supervisorComboBox.addItem(employee);
                }
                if (employeeChosen.getSupervisor() != null){
                    supervisorComboBox.setSelectedItem(employeeChosen.getSupervisor());
                }
                formPanel.add(supervisorLabel);
                formPanel.add(supervisorComboBox);

                positionLabel = new JLabel("Fonction * :");
                positionComboBox = new JComboBox<>();
                positionsList = controller.listFonctions();
                for (Fonction fonction: positionsList) {
                    positionComboBox.addItem(fonction);
                }
                if (employeeChosen.getPosition() != null){
                    positionComboBox.setSelectedItem(employeeChosen.getPosition());
                }
                formPanel.add(positionLabel);
                formPanel.add(positionComboBox);

                nbChildsLabel = new JLabel("Nombre d'enfants * :");
                nbChildsField = new JTextField(10);
                nbChildsField.setText(Integer.toString(employeeChosen.getNbChilds()));
                formPanel.add(nbChildsLabel);
                formPanel.add(nbChildsField);

                sexLabel = new JLabel("Sexe * :");
                maleButton = new JRadioButton("Mâle");
                femaleButton = new JRadioButton("Femelle");
                unknownButton = new JRadioButton("Inconnu");
                ButtonGroup groupSex = new ButtonGroup();
                groupSex.add(maleButton);
                if (employeeChosen.getSex() == 'M'){
                    maleButton.setSelected(true);
                } else if (employeeChosen.getSex() == 'F'){
                    femaleButton.setSelected(true);
                } else {
                    unknownButton.setSelected(true);
                }
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
                model.setValue(employeeChosen.getBirthday());
                spinner = new JSpinner(model);
                JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd.MM.yyyy");
                DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
                formatter.setAllowsInvalid(false);
                formatter.setOverwriteMode(true);
                formPanel.add(birthdayLabel);
                formPanel.add(spinner);

                isMarriedLabel = new JLabel("Etes-vous marrié ?");
                isMarriedBox = new JCheckBox();
                isMarriedBox.setSelected(employeeChosen.getMArried());
                formPanel.add(isMarriedLabel);
                formPanel.add(isMarriedBox);

                phoneNumberLabel = new JLabel("Numéro de téléphone : ");
                phoneNumberField = new JTextField(20);
                phoneNumberField.setText(employeeChosen.getPhoneNumber());
                formPanel.add(phoneNumberLabel);
                formPanel.add(phoneNumberField);

                submitButton = new JButton("Modifier");
                cancelButton = new JButton("Annuler");
                submitButton.addActionListener(this);
                cancelButton.addActionListener(this);

                formPanel.add(submitButton);
                formPanel.add(cancelButton);
                add(formPanel, BorderLayout.CENTER);
                revalidate();
                repaint();

            }  catch (ListFonctionsException | ListEmployeesException exception) {
                JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Modifier")){
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            Employee supervisor = (Employee) supervisorComboBox.getSelectedItem();
            String supervisorId = supervisor.getMatricule();

            Fonction position = (Fonction) positionComboBox.getSelectedItem();
            String positionId = position.getId();

            String nbChildsInfo = nbChildsField.getText();
            char sex;
            if (maleButton.isSelected()){
                sex = 'M';
            } else if (femaleButton.isSelected()){
                sex = 'F';
            } else {
                sex = 'X';
            }
            Date birthday = model.getDate();
            Boolean isMarried = isMarriedBox.isSelected();
            String phoneNumber = phoneNumberField.getText();
            if (phoneNumberField.getText().isEmpty()){
                phoneNumber = "";
            }
            Employee employeeChosen;
            Boolean matriculeNotFree = false;
            try {
                matriculeNotFree = controller.employeeExisting(currentMatricule);
            } catch (EmployeeIsExisting exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }

            if (matriculeNotFree) {
                JOptionPane.showMessageDialog(null, "Le matricule choisi n'est pas disponible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                if (currentMatricule.length() > 20 || currentMatricule.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Le matricule doit contenir entre 1 et 20 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (lastName.length() > 20 || lastName.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Le nom doit contenir entre 1 et 20 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (!lastName.matches("[\\p{L}]+")) {
                            JOptionPane.showMessageDialog(null, "Le nom doit contenir uniquement des lettres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (firstName.length() > 20 || firstName.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Le prénom doit contenir entre 1 et 20 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            } else {
                                if (!firstName.matches("[\\p{L}]+")) {
                                    JOptionPane.showMessageDialog(null, "Le prénom doit contenir uniquement des lettres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    if (nbChildsInfo.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Le nombre d'enfants ne doit pas être vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        if (!nbChildsInfo.matches("-?\\d+(\\.\\d+)?")) {
                                            JOptionPane.showMessageDialog(null, "Le champ nombre d'enfants doit contenir seulement des chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            if (nbChildsInfo.length() > 10) {
                                                JOptionPane.showMessageDialog(null, "Le nombre d'enfants ne doit pas dépasser 10 chiffres", "Erreur", JOptionPane.ERROR_MESSAGE);
                                            } else {
                                                int nbChilds = Integer.parseInt(nbChildsInfo);
                                                if (nbChilds < 0) {
                                                    JOptionPane.showMessageDialog(null, "Le nombre d'enfants doit être positif", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    Date now = new Date();
                                                    if (birthday.after(now) || birthday.equals(now)) {
                                                        JOptionPane.showMessageDialog(null, "Votre anniversaire doit être antérieur à la date actuelle.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                    } else {
                                                        if (!phoneNumber.isEmpty()) {
                                                            if (phoneNumber.length() > 20) {
                                                                JOptionPane.showMessageDialog(null, "Le numéro de téléphone doit contenir entre 1 et 20 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                            } else if (!phoneNumber.matches("^(\\+\\d{1,3})?\\s?\\d{3,}\\s?\\d{2,}\\s?\\d{2,}$")) {
                                                                JOptionPane.showMessageDialog(null, "Le numéro de téléphone est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                            } else {
                                                                try {
                                                                    employeeChosen = new Employee(currentMatricule, lastName, firstName, supervisorId, positionId, nbChilds, sex, birthday, isMarried, phoneNumber);
                                                                    controller.modifyEmployee(employeeChosen);

                                                                    JOptionPane.showMessageDialog(null, "La modification de l'employé a été effectué", "Réussite", JOptionPane.INFORMATION_MESSAGE);
                                                                    revalidate();

                                                                    removeAll();
                                                                    add(panel,BorderLayout.NORTH);
                                                                    allEmployees.clear();
                                                                    comboBox = new JComboBox<>();
                                                                    try {
                                                                        allEmployees = controller.listEmployees();
                                                                    }catch (ListEmployeesException exception){
                                                                        JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
                                                                    }
                                                                    for (Employee employee: allEmployees) {
                                                                        comboBox.addItem(employee);
                                                                    }
                                                                    panel.add(matriculeLabel);
                                                                    panel.add(comboBox);
                                                                    panel.add(submitChoiceButton);
                                                                    revalidate();
                                                                    repaint();
                                                                } catch (ModifyEmployeeException exception) {
                                                                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                                                                }
                                                            }
                                                        } else {
                                                            try {
                                                                employeeChosen = new Employee(currentMatricule, lastName, firstName, supervisorId, positionId, nbChilds, sex, birthday, isMarried);
                                                                controller.modifyEmployee(employeeChosen);
                                                                JOptionPane.showMessageDialog(null, "La modification de l'employé a été effectué", "Réussite", JOptionPane.INFORMATION_MESSAGE);
                                                                revalidate();

                                                                removeAll();
                                                                add(panel,BorderLayout.NORTH);
                                                                allEmployees.clear();
                                                                comboBox = new JComboBox<>();
                                                                try {
                                                                    allEmployees = controller.listEmployees();
                                                                }catch (ListEmployeesException exception){
                                                                    JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
                                                                }
                                                                for (Employee employee: allEmployees) {
                                                                    comboBox.addItem(employee);
                                                                }
                                                                panel.add(matriculeLabel);
                                                                panel.add(comboBox);
                                                                panel.add(submitChoiceButton);
                                                                revalidate();
                                                                repaint();
                                                            } catch (ModifyEmployeeException exception) {
                                                                JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else {
            if (e.getActionCommand().equals("Annuler")) {
                removeAll();
                add(panel,BorderLayout.NORTH);
                allEmployees.clear();
                comboBox = new JComboBox<>();
                try {
                    allEmployees = controller.listEmployees();
                }catch (ListEmployeesException exception){
                    JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
                }
                for (Employee employee: allEmployees) {
                    comboBox.addItem(employee);
                }
                panel.add(matriculeLabel);
                panel.add(comboBox);
                panel.add(submitChoiceButton);
                revalidate();
                repaint();
            }
        }
    }
}
