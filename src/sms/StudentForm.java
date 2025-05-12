package sms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentForm extends JFrame 
{
    private JTextField idField, nameField, emailField;
    private JButton addButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentManager manager;

    public StudentForm() {
        manager = new StudentManager();
        setTitle("Student Management System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(120, 20, 150, 25);
        add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 60, 150, 25);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 100, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 100, 150, 25);
        add(emailField);

        addButton = new JButton("Add Student");
        addButton.setBounds(120, 140, 150, 30);
        add(addButton);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email"}, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(20, 190, 440, 150);
        add(scrollPane);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    Student student = new Student(id, name, email);
                    manager.addStudent(student);
                    tableModel.addRow(new Object[]{id, name, email});
                    idField.setText("");
                    nameField.setText("");
                    emailField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid ID format.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentForm form = new StudentForm();
            form.setVisible(true);
        });
    }
}