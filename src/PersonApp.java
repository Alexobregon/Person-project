import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

public class PersonApp {
    private PersonController personController;

    public PersonApp() {
        personController = new PersonController();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Person Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        frame.add(inputPanel, BorderLayout.NORTH);

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameTextField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameTextField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageTextField = new JTextField();

        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameTextField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameTextField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageTextField);

        JLabel methodLabel = new JLabel("Method:");
        JTextField methodTextField = new JTextField();
        JButton methodButton = new JButton("Execute Method");

        inputPanel.add(methodLabel);
        inputPanel.add(methodTextField);
        inputPanel.add(methodButton);

        DefaultListModel<String> personListModel = new DefaultListModel<>();
        JList<String> personList = new JList<>(personListModel);
        frame.add(new JScrollPane(personList), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Person");
        inputPanel.add(addButton);

        JButton deleteButton = new JButton("Delete Person");
        inputPanel.add(deleteButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                int age = Integer.parseInt(ageTextField.getText());

                personController.addPerson(firstName, lastName, age);
                personListModel.addElement(firstName + " " + lastName + " (Age: " + age + ")");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = personList.getSelectedIndex();
                personController.deletePerson(selectedIndex);
                personListModel.remove(selectedIndex);
            }
        });

        methodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = personList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String methodName = methodTextField.getText();
                    Person selectedPerson = personController.getPersons().get(selectedIndex);
                    try {
                        Method method = selectedPerson.getClass().getMethod(methodName);
                        Object result = method.invoke(selectedPerson);
                        JOptionPane.showMessageDialog(frame, "Result: " + result.toString());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a person first.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PersonApp();
            }
        });
    }
}
