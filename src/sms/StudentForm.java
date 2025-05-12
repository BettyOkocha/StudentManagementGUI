package sms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentForm extends JFrame {
    private JTextField idField, nameField, emailField, searchField;
    private JButton addButton, deleteButton, updateButton, searchButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentManager manager;

    public StudentForm() {
        manager = new StudentManager();
        setTitle("Student Management System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color to blue
        getContentPane().setBackground(new Color(173, 216, 230));  // Light blue background

        // ID Label and TextField
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(120, 20, 150, 25);
        add(idField);

        // Name Label and TextField
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 60, 150, 25);
        add(nameField);

        // Email Label and TextField
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 100, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 100, 150, 25);
        add(emailField);

        // Buttons for Add, Update, Delete, and Search
        addButton = new JButton("Add");
        addButton.setBounds(20, 140, 100, 30);
        add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(130, 140, 100, 30);
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(240, 140, 100, 30);
        add(deleteButton);

        searchButton = new JButton("Search");
        searchButton.setBounds(350, 140, 100, 30);
        add(searchButton);

        // Search Field for Search functionality
        JLabel searchLabel = new JLabel("Search by Name:");
        searchLabel.setBounds(20, 180, 120, 25);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(140, 180, 200, 25);
        add(searchField);

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email"}, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(20, 220, 540, 200);
        add(scrollPane);

        // Add Button Action
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

        // Update Button Action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = Integer.parseInt(idField.getText().trim());
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    manager.getStudents().get(selectedRow).setName(name);
                    manager.getStudents().get(selectedRow).setEmail(email);
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(email, selectedRow, 2);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a student to update.");
                }
            }
        });

        // Delete Button Action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    manager.getStudents().remove(selectedRow);
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a student to delete.");
                }
            }
        });

        // Search Button Action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim().toLowerCase();
                tableModel.setRowCount(0); // Clear table

                for (Student student : manager.getStudents()) {
                    if (student.getName().toLowerCase().contains(searchTerm)) {
                        tableModel.addRow(new Object[]{student.getId(), student.getName(), student.getEmail()});
                    }
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
