import java.sql.*;
import java.util.Scanner;

public class TodoApp {
    // === MySQL connection setup ===
    static final String DB_URL = "jdbc:mysql://localhost:3306/todo_app";
    static final String USER = "root";             // Change to your MySQL username
    static final String PASS = "your_password";    // Change to your MySQL password

    // Optional: Load MySQL driver (good for compatibility)
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== TODO App ===");
            System.out.println("1. View tasks");
            System.out.println("2. Add task");
            System.out.println("3. Mark task as done");
            System.out.println("4. Delete task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> viewTasks();
                    case 2 -> {
                        System.out.print("Enter task description: ");
                        String desc = scanner.nextLine();
                        addTask(desc);
                    }
                    case 3 -> {
                        System.out.print("Enter task ID to mark as done: ");
                        int doneId = Integer.parseInt(scanner.nextLine());
                        markTaskAsDone(doneId);
                    }
                    case 4 -> {
                        System.out.print("Enter task ID to delete: ");
                        int delId = Integer.parseInt(scanner.nextLine());
                        deleteTask(delId);
                    }
                    case 5 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
            }
        }
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private static void addTask(String description) {
        String sql = "INSERT INTO tasks (description) VALUES (?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, description);
            stmt.executeUpdate();
            System.out.println("✅ Task added.");
        } catch (SQLException e) {
            System.out.println("❌ Error adding task: " + e.getMessage());
        }
    }

    private static void viewTasks() {
        String sql = "SELECT * FROM tasks";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Tasks ---");
            while (rs.next()) {
                System.out.printf("%d. %s [%s]\n",
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getBoolean("is_done") ? "Done" : "Pending");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching tasks: " + e.getMessage());
        }
    }

    private static void markTaskAsDone(int id) {
        String sql = "UPDATE tasks SET is_done = TRUE WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Task marked as done.");
            } else {
                System.out.println("❌ Task not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating task: " + e.getMessage());
        }
    }

    private static void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("🗑️ Task deleted.");
            } else {
                System.out.println("❌ Task not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting task: " + e.getMessage());
        }
    }
}
