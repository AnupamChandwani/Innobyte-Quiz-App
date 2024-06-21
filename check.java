import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;
import java.sql.*;

public class check {

    private JFrame frame;
    private JPanel quizPanel;
    private CardLayout cardLayout;
    private Map<String, String[][]> quizData; // Stores questions and answers for each topic
    private int currentQuestionIndex; // Track the current question index
    private int userScore; // Track the user's score
    private ButtonGroup optionsGroup; // To group radio buttons for answer choices
    private String[][] currentQuestions; // Store current topic's questions
    private String selectedTopic; // Current quiz topic
private String username;
    // Main method for testing
    public static void main(String[] args) {
        new check("testuser");
    }

    public check(String username) {
        this.username = username;
        // Frame setup
        frame = new JFrame("Check");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1380, 720);
        frame.setLayout(null); // Not recommended, better to use layout managers

        // Left background panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(128, 128, 128)); // Soft Coral color
        leftPanel.setBounds(0, 0, 245, frame.getHeight());
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        // Add clickable labels to the left background
        addClickableLabels(leftPanel);

        // Modular boxes panel
        JPanel modularBoxPanel = new JPanel();
        modularBoxPanel.setLayout(new BoxLayout(modularBoxPanel, BoxLayout.X_AXIS));
        modularBoxPanel.setBounds(255, 50, 1100, 300);
        frame.add(modularBoxPanel);

        // Add modular boxes
        for (int i = 1; i <= 4; i++) {
            JPanel box = createStyledBox(i, 300, 300);
            modularBoxPanel.add(box);
            if (i < 4) {
                modularBoxPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            }
        }

        // Scroll pane for additional boxes
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(255, 400, 1100, 300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);

        JPanel additionalBoxPanel = new JPanel();
        additionalBoxPanel.setLayout(new GridLayout(3, 3, 10, 10));
        scrollPane.setViewportView(additionalBoxPanel);

        // Initialize quiz data for different topics
        initializeQuizData();

        // Create clickable boxes for different topics
        String[] topics = {"General Knowledge", "Science", "History", "Math", "Geography", "Literature", "Sports", "Art"};
        for (String topic : topics) {
            JPanel box = createClickableBox(topic, quizData.get(topic).length, 150);
            additionalBoxPanel.add(box);
        }

        // Card layout for switching panels
        cardLayout = new CardLayout();
        quizPanel = new JPanel(cardLayout);
        quizPanel.setBounds(255, 50, 1100, 620);
        frame.add(quizPanel);

        // Main panel with modular boxes
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBounds(0, 0, 1100, 620);
        mainPanel.add(modularBoxPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        quizPanel.add(mainPanel, "MainPanel");

        // Show the main panel initially
        cardLayout.show(quizPanel, "MainPanel");

        frame.setVisible(true);
    }

    private void initializeQuizData() {
        quizData = new HashMap<>();
        quizData.put("General Knowledge", new String[][] {
            {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "Paris"},
            {"Who wrote 'Hamlet'?", "Shakespeare", "Dickens", "Chaucer", "Austen", "Shakespeare"},
            {"Which planet is known as the Red Planet?", "Mars", "Earth", "Jupiter", "Venus", "Mars"},
            {"What is the largest ocean?", "Pacific", "Atlantic", "Indian", "Arctic", "Pacific"},
            {"Who painted the Mona Lisa?", "Leonardo da Vinci", "Van Gogh", "Picasso", "Michelangelo", "Leonardo da Vinci"}
        });
        quizData.put("Science", new String[][] {
            {"What is the chemical symbol for water?", "H2O", "O2", "CO2", "H2", "H2O"},
            {"What planet is closest to the Sun?", "Mercury", "Venus", "Earth", "Mars", "Mercury"},
            {"What is the speed of light?", "299,792,458 m/s", "150,000,000 m/s", "100,000,000 m/s", "3,000,000 m/s", "299,792,458 m/s"},
            {"What gas do plants absorb from the atmosphere?", "Carbon Dioxide", "Oxygen", "Nitrogen", "Hydrogen", "Carbon Dioxide"},
            {"What is the powerhouse of the cell?", "Mitochondria", "Nucleus", "Ribosome", "Endoplasmic Reticulum", "Mitochondria"}
        });
        quizData.put("History", new String[][] {
            {"Who was the first President of the United States?", "George Washington", "Abraham Lincoln", "Thomas Jefferson", "John Adams", "George Washington"},
            {"What year did World War II end?", "1945", "1940", "1939", "1942", "1945"},
            {"Who was the famous queen of Ancient Egypt?", "Cleopatra", "Nefertiti", "Hatshepsut", "Nefertari", "Cleopatra"},
            {"Which empire was known for its road system?", "Roman Empire", "Greek Empire", "Ottoman Empire", "Persian Empire", "Roman Empire"},
            {"What wall separated East and West Berlin?", "Berlin Wall", "Great Wall of China", "Iron Curtain", "Hadrian's Wall", "Berlin Wall"}
        });
        quizData.put("Math", new String[][] {
            {"What is 2+2?", "4", "3", "5", "6", "4"},
            {"What is the square root of 64?", "8", "6", "7", "9", "8"},
            {"What is 7*8?", "56", "48", "54", "60", "56"},
            {"What is the value of pi?", "3.14159", "3.14", "2.718", "1.618", "3.14159"},
            {"What is the perimeter of a square with side length 5?", "20", "10", "15", "25", "20"}
        });
        quizData.put("Geography", new String[][] {
            {"Which continent is the Sahara Desert located on?", "Africa", "Asia", "Australia", "South America", "Africa"},
            {"What is the longest river in the world?", "Nile", "Amazon", "Yangtze", "Mississippi", "Nile"},
            {"What is the capital of Japan?", "Tokyo", "Kyoto", "Osaka", "Nagoya", "Tokyo"},
            {"Which country has the largest population?", "China", "India", "USA", "Indonesia", "China"},
            {"What is the smallest country in the world?", "Vatican City", "Monaco", "San Marino", "Liechtenstein", "Vatican City"}
        });
        quizData.put("Literature", new String[][] {
            {"Who wrote '1984'?", "George Orwell", "Aldous Huxley", "Ray Bradbury", "J.K. Rowling", "George Orwell"},
            {"What is the first book of the Bible?", "Genesis", "Exodus", "Leviticus", "Numbers", "Genesis"},
            {"Who is the author of 'Pride and Prejudice'?", "Jane Austen", "Charlotte Bronte", "Emily Bronte", "George Eliot", "Jane Austen"},
            {"What is the main theme of 'Moby Dick'?", "Obsession", "Love", "War", "Friendship", "Obsession"},
            {"Who wrote 'The Great Gatsby'?", "F. Scott Fitzgerald", "Ernest Hemingway", "Mark Twain", "John Steinbeck", "F. Scott Fitzgerald"}
        });
        quizData.put("Sports", new String[][] {
            {"Which country won the FIFA World Cup in 2018?", "France", "Germany", "Brazil", "Argentina", "France"},
            {"How many players are on a basketball team?", "5", "6", "7", "11", "5"},
            {"Which sport is known as the 'king of sports'?", "Soccer", "Basketball", "Baseball", "Tennis", "Soccer"},
            {"Who holds the record for the most home runs in a single MLB season?", "Barry Bonds", "Babe Ruth", "Hank Aaron", "Sammy Sosa", "Barry Bonds"},
            {"What is the highest score in a single game of bowling?", "300", "200", "400", "500", "300"}
        });
        quizData.put("Art", new String[][] {
            {"Who painted the ceiling of the Sistine Chapel?", "Michelangelo", "Raphael", "Leonardo da Vinci", "Donatello", "Michelangelo"},
            {"What is the art style of Picasso's 'Guernica'?", "Cubism", "Surrealism", "Impressionism", "Realism", "Cubism"},
            {"Which artist is famous for his 'Starry Night' painting?", "Van Gogh", "Monet", "Rembrandt", "Renoir", "Van Gogh"},
            {"What is the medium of 'The Persistence of Memory' by Salvador Dali?", "Oil on canvas", "Watercolor", "Acrylic", "Fresco", "Oil on canvas"},
            {"What movement was Andy Warhol a leading figure in?", "Pop Art", "Abstract Expressionism", "Surrealism", "Dada", "Pop Art"}
        });
    }

    private JPanel createStyledBox(int index, int width, int height) {
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(width, height));
        box.setBackground(new Color(173, 216, 230)); // Very light blue color
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Thick black border (3 pixels)
        box.setLayout(new BorderLayout()); // Use BorderLayout for better control

        // Determine the box name and image
        String mboxname = "";
        String imagePath = "";
        switch (index) {
            case 1:
                mboxname = "Quiz Creator for Teachers";
                imagePath = "C:/Users/User/Desktop/teacher123.jpeg";
                break;
            case 2:
                mboxname = "Quiz Creator for Business";
                imagePath = "C:/Users/User/Desktop/Business.jpeg";
                break;
            case 3:
                mboxname = "Quiz Creator for Employers";
                imagePath = "C:/Users/User/Desktop/download.jpeg";
                break;
            case 4:
                mboxname = "Quiz Creator for Students";
                imagePath = "C:/Users/User/Desktop/Students.jpeg";
                break;
        }

        // Add the label with mboxname to the top
        JLabel nameLabel = new JLabel(mboxname);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setForeground(Color.BLACK); // Text color
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        box.add(nameLabel, BorderLayout.NORTH);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        ImageIcon originalIcon = new ImageIcon(imagePath);

        Image originalImage = originalIcon.getImage();

        int newWidth = 250; // Example: reduce width by half
        int newHeight = 100; // Example: reduce height by half

        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        imageLabel.setIcon(resizedIcon);

        box.add(imageLabel, BorderLayout.CENTER);

        // Add a button below the image (without any event listener)
        JButton dummyButton = new JButton("Buy Now");
        dummyButton.setPreferredSize(new Dimension(width, 30)); // Set preferred size to control height
        dummyButton.setEnabled(false); // Disable the button to make it unclickable
        box.add(dummyButton, BorderLayout.SOUTH);

        return box;
    }

    private JPanel createClickableBox(String topic, int questionCount, int boxSize) {
        JPanel box = new JPanel(new BorderLayout());
        box.setPreferredSize(new Dimension(boxSize, boxSize));
        box.setBackground(new Color(173, 216, 230)); // Very light blue color
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Thick black border (4 pixels)

        JLabel titleLabel = new JLabel(topic, SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK); // Change text color to black for better contrast
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        box.add(titleLabel, BorderLayout.CENTER);

        JLabel countLabel = new JLabel("Questions: " + questionCount, SwingConstants.CENTER);
        countLabel.setForeground(Color.BLACK); // Change text color to black for better contrast
        box.add(countLabel, BorderLayout.SOUTH);

        box.setCursor(new Cursor(Cursor.HAND_CURSOR));
        box.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedTopic = topic;
                currentQuestions = quizData.get(topic);
                currentQuestionIndex = 0;
                userScore = 0;
                showNextQuestion();
            }
        });

        return box;
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < currentQuestions.length) {
            JPanel questionPanel = new JPanel(null);
            questionPanel.setBounds(50, 50, 1000, 500); // Adjust the bounds as per your layout

            String[] currentQuestion = currentQuestions[currentQuestionIndex];
            JLabel questionLabel = new JLabel(currentQuestion[0]);
            questionLabel.setBounds(50, 50, 1000, 30);
            questionLabel.setForeground(Color.BLACK);
            questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
            questionPanel.add(questionLabel);

            optionsGroup = new ButtonGroup();
            for (int i = 1; i <= 4; i++) {
                JRadioButton optionButton = new JRadioButton(currentQuestion[i]);
                optionButton.setBounds(50, 100 + (i - 1) * 50, 300, 30);
                optionButton.setForeground(Color.BLACK);
                optionButton.setFont(new Font("Arial", Font.PLAIN, 18));
                optionsGroup.add(optionButton);
                questionPanel.add(optionButton);
            }

            JButton nextButton = new JButton("Next");
            JButton previousButton = new JButton("Previous");
            JButton submitButton = new JButton("Submit");
            submitButton.setBounds(270, 300, 100, 30);
            nextButton.setBounds(160, 300, 100, 30);
            previousButton.setBounds(50, 300, 100, 30);

            // Disable the Previous button if this is the first question
            if (currentQuestionIndex == 0) {
                previousButton.setEnabled(false);
            } else {
                previousButton.setEnabled(true);
            }

            // Enable submit button on the last question
            if (currentQuestionIndex == currentQuestions.length - 1) {
                submitButton.setEnabled(true);
                nextButton.setEnabled(false);
            } else {
                submitButton.setEnabled(false);
                nextButton.setEnabled(true);
            }

            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    checkAnswer(currentQuestion[5]);
                    currentQuestionIndex++;
                    showNextQuestion();
                }
            });

            previousButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentQuestionIndex--;
                    showNextQuestion();
                }
            });

            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    checkAnswer(currentQuestion[5]); // Check the answer for the current question
                    // Display the user's score in a dialog box
                    JOptionPane.showMessageDialog(frame, "Your Score: " + userScore + " out of " + currentQuestions.length);
                    // Redirect to the main menu
                    cardLayout.show(quizPanel, "MainPanel");
                }
            });

            questionPanel.add(nextButton);
            questionPanel.add(previousButton);
            questionPanel.add(submitButton);

            // Adding the question panel to the quiz panel and showing it
            quizPanel.add(questionPanel, "Question" + currentQuestionIndex);
            cardLayout.show(quizPanel, "Question" + currentQuestionIndex);
        } else {
            showScore();
        }
    }

    private void checkAnswer(String correctAnswer) {
        for (Enumeration<AbstractButton> buttons = optionsGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected() && button.getText().equals(correctAnswer)) {
                userScore++;
                break;
            }
        }
    }

    private void showScore() {
        // Display the user's score in a dialog box
        JOptionPane.showMessageDialog(frame, "Your Score: " + userScore + " out of " + currentQuestions.length);
        // Redirect to the main menu
        cardLayout.show(quizPanel, "MainPanel");
    }

    private void addClickableLabels(JPanel leftBackground) {
        String[] labels = {"Profile", "Subscription", "History"};
        int labelHeight = 40;
        int yOffset = 250; // Starting position for the first label
        int spacing = 20; // Space between labels

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i], SwingConstants.CENTER);
            label.setBounds(0, yOffset + i * (labelHeight + spacing), leftBackground.getWidth(), labelHeight);
            label.setForeground(Color.WHITE); // Set text color to white
            label.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and style
            label.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering

            // Add mouse listener to handle clicks
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (label.getText().equals("Profile")) {
                        showUserProfile(); // Show user profile details
                    } else {
                        JOptionPane.showMessageDialog(frame, "You clicked on: " + label.getText());
                        // Handle other label click actions (e.g., navigate to subscription, history)
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setForeground(Color.YELLOW); // Change text color on hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setForeground(Color.WHITE); // Reset text color after hover
                }
            });

            leftBackground.add(label);
        }
    }
private void showUserProfile() {
        // Query to fetch user details
        String query = "SELECT name, email, phone FROM users WHERE username = ?";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username); // Set username parameter
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                
                // Display user details in a dialog or panel
                JPanel profilePanel = new JPanel(new GridLayout(3, 2));
                profilePanel.add(new JLabel("Name:"));
                profilePanel.add(new JLabel(name));
                profilePanel.add(new JLabel("Email:"));
                profilePanel.add(new JLabel(email));
                profilePanel.add(new JLabel("Phone:"));
                profilePanel.add(new JLabel(phone));
                
                JOptionPane.showMessageDialog(frame, profilePanel, "Profile Details", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "User details not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
