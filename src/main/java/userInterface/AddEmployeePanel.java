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
            String supervisorId = supervisor.getMatricule();

            Fonction position = (Fonction) positionComboBox.getSelectedItem();
            String positionId = position.getId();

            String nbChildsInfo = nbChildsField.getText();
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

            Employee employee;
            Boolean matriculeNotFree = false;
            try {
                matriculeNotFree = controller.EmployeeExisting(matricule);
            } catch (EmployeeIsExisting exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }

            if (matriculeNotFree) {
                JOptionPane.showMessageDialog(null,"Le matricule choisi n'est pas disponible.","Erreur",JOptionPane.ERROR_MESSAGE);
            } else {
                if (matricule.length() > 20 || matricule.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Le code doit contenir entre 1 et 20 caractères.","Erreur",JOptionPane.ERROR_MESSAGE);
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
                                            JOptionPane.showMessageDialog(null, "Le champ poids doit contenir seulement des chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            if (nbChildsInfo.length() > 10) {
                                                JOptionPane.showMessageDialog(null, "Le nombre d'enfants ne doit pas dépasser 10 chiffres", "Erreur", JOptionPane.ERROR_MESSAGE);
                                            } else {
                                                int nbChilds = Integer.parseInt(nbChildsInfo);
                                                if (nbChilds < 0) {
                                                    JOptionPane.showMessageDialog(null, "Le nombre d'enfants doit être positif", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    Date now = new Date();
                                                    if (birthday.after(now) | birthday == now) {
                                                        JOptionPane.showMessageDialog(null, "Votre anniversaire doit être antérieur à la date avtuelle.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                    } else {
                                                        if (!phoneNumber.isEmpty()) {
                                                            if (phoneNumber.length() > 20) {
                                                                JOptionPane.showMessageDialog(null, "Le numéro de téléphone doit contenir entre 1 et 20 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                            } else if (!phoneNumber.matches("^(\\+\\d{1,3})?\\s?\\d{3,}\\s?\\d{2,}\\s?\\d{2,}$")) {
                                                                JOptionPane.showMessageDialog(null, "Le surnom doit contenir uniquement des lettres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                            } else {
                                                                try {
                                                                    employee = new Employee(matricule, lastName, firstName, supervisorId, positionId, nbChilds, sex, birthday, isMarried, phoneNumber);
                                                                    controller.add(newAnimal);
                                                                    codeField.setText("");
                                                                    nameField.setText("");
                                                                    weightField.setText("");
                                                                    sexFemaleButton.setSelected(false);
                                                                    sexMaleButton.setSelected(true);
                                                                    nickNameField.setText("");
                                                                    JOptionPane.showMessageDialog(null, "L'ajout de l'animal a été effectué", "Réussite", JOptionPane.INFORMATION_MESSAGE);
                                                                    revalidate();
                                                                } catch (AddAnimalException exception) {
                                                                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                                                                }
                                                            }
                                                        } else {
                                                            try {
                                                                newAnimal = new Animal(code, name, arrivalDate, sex, isDangerous, weight, breed);
                                                                controller.addAnimal(newAnimal);
                                                                codeField.setText("");
                                                                nameField.setText("");
                                                                weightField.setText("");
                                                                sexFemaleButton.setSelected(false);
                                                                sexMaleButton.setSelected(true);
                                                                nickNameField.setText("");
                                                                JOptionPane.showMessageDialog(null, "L'ajout de l'animal a été effectué", "Réussite", JOptionPane.INFORMATION_MESSAGE);
                                                                revalidate();
                                                            } catch (AddAnimalException exception) {
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
            } else if (e.getActionCommand().equals("Annuler")) {
                codeField.setText("");
                nameField.setText("");
                weightField.setText("");
                sexFemaleButton.setSelected(false);
                sexMaleButton.setSelected(true);
                nickNameField.setText("");
                revalidate();
            }
        }
    }
}
