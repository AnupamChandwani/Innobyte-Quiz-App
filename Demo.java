import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Demo {
    private JFrame jframe;
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton, signupButton;

    private JLabel signupUsernameLabel, nameLabel, emailLabel, phoneLabel, signupPasswordLabel;
    private JTextField signupUsernameTextField, nameTextField, emailTextField, phoneTextField;
    private JPasswordField signupPasswordTextField;
    private JButton backButton, saveButton;

    public Demo() {
        jframe = new JFrame();
        jframe.setTitle("Quiz Creation App");
        jframe.setSize(1200, 640);
        jframe.setLayout(null);

        titleLabel = new JLabel("Quiz Creation App", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 55));
        titleLabel.setBounds(400, 20, 500, 80);
        jframe.add(titleLabel);

        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Serif", Font.BOLD, 21));
        usernameLabel.setBounds(450, 200, 150, 30);
        jframe.add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(550, 200, 300, 30);
        jframe.add(usernameTextField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 21));
        passwordLabel.setBounds(450, 250, 150, 30);
        jframe.add(passwordLabel);

        passwordTextField = new JPasswordField();
        passwordTextField.setBounds(550, 250, 300, 30);
        jframe.add(passwordTextField);

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(550, 350, 90, 30);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        jframe.add(loginButton);

        signupButton = new JButton("SIGN UP");
        signupButton.setBounds(650, 350, 90, 30);
        signupButton.setBackground(new Color(70, 130, 180));
        signupButton.setForeground(Color.WHITE);
        jframe.add(signupButton);

        signupUsernameLabel = new JLabel("Username");
        signupUsernameLabel.setFont(new Font("Serif", Font.BOLD, 21));
        signupUsernameLabel.setBounds(450, 200, 150, 30);
        jframe.add(signupUsernameLabel);

        signupUsernameTextField = new JTextField();
        signupUsernameTextField.setBounds(550, 200, 300, 30);
        jframe.add(signupUsernameTextField);

        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 21));
        nameLabel.setBounds(450, 250, 150, 30);
        jframe.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(550, 250, 300, 30);
        jframe.add(nameTextField);

        emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Serif", Font.BOLD, 21));
        emailLabel.setBounds(450, 300, 150, 30);
        jframe.add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setBounds(550, 300, 300, 30);
        jframe.add(emailTextField);

        phoneLabel = new JLabel("Phone");
        phoneLabel.setFont(new Font("Serif", Font.BOLD, 21));
        phoneLabel.setBounds(450, 350, 150, 30);
        jframe.add(phoneLabel);

        phoneTextField = new JTextField();
        phoneTextField.setBounds(550, 350, 300, 30);
        jframe.add(phoneTextField);

        signupPasswordLabel = new JLabel("Password");
        signupPasswordLabel.setFont(new Font("Serif", Font.BOLD, 21));
        signupPasswordLabel.setBounds(450, 400, 150, 30);
        jframe.add(signupPasswordLabel);

        signupPasswordTextField = new JPasswordField();
        signupPasswordTextField.setBounds(550, 400, 300, 30);
        jframe.add(signupPasswordTextField);

        backButton = new JButton("BACK");
        backButton.setBounds(550, 500, 90, 30);
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        jframe.add(backButton);

        saveButton = new JButton("SAVE");
        saveButton.setBounds(650, 500, 90, 30);
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        jframe.add(saveButton);

        toggleSignupComponents(false);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleLoginComponents(false);
                toggleSignupComponents(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleSignupComponents(false);
                toggleLoginComponents(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signup();
            }
        });

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }

    private void toggleLoginComponents(boolean visible) {
        usernameLabel.setVisible(visible);
        usernameTextField.setVisible(visible);
        passwordLabel.setVisible(visible);
        passwordTextField.setVisible(visible);
        loginButton.setVisible(visible);
        signupButton.setVisible(visible);
    }

    private void toggleSignupComponents(boolean visible) {
        signupUsernameLabel.setVisible(visible);
        signupUsernameTextField.setVisible(visible);
        nameLabel.setVisible(visible);
        nameTextField.setVisible(visible);
        emailLabel.setVisible(visible);
        emailTextField.setVisible(visible);
        phoneLabel.setVisible(visible);
        phoneTextField.setVisible(visible);
        signupPasswordLabel.setVisible(visible);
        signupPasswordTextField.setVisible(visible);
        backButton.setVisible(visible);
        saveButton.setVisible(visible);
    }

    private void login() {
        String username = usernameTextField.getText();
        String password = new String(passwordTextField.getPassword());

        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(jframe, "Login successful!");
                showCheckScreen(username);
            } else {
                JOptionPane.showMessageDialog(jframe, "Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void signup() {
        String username = signupUsernameTextField.getText();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String phone = phoneTextField.getText();
        String password = new String(signupPasswordTextField.getPassword());

        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO users (username, name, email, phone, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(jframe, "Signup successful!");
                toggleSignupComponents(false);
                toggleLoginComponents(true);
            } else {
                JOptionPane.showMessageDialog(jframe, "Signup failed. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showCheckScreen(String username) {
        jframe.dispose();  // Close the current frame
        new check(username);  // Launch the new screen with the username
    }

    public static void main(String[] args) {
        new Demo();
    }
}

class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/quiz_app";
    private static final String USER = "root";
    private static final String PASSWORD = "#Anupam123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
