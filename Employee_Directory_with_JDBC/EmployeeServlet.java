import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    // Database connection method
    private Connection getConnection() throws SQLException {
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/your_database_name";
            String username = "root";
            String password = "your_password";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database connection error.");
        }
    }

    // Method to fetch all employees
    private List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Employee employee = new Employee(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("designation")
                );
                employees.add(employee);
            }
        }
        return employees;
    }

    // Method to fetch employee by ID
    private Employee getEmployeeById(int id) throws SQLException {
        String query = "SELECT * FROM employees WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("designation")
                    );
                }
            }
        }
        return null; // Return null if no employee is found
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employeeId");
        List<Employee> employees = new ArrayList<>();

        if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(employeeIdStr);
                Employee employee = getEmployeeById(employeeId);
                if (employee != null) {
                    employees.add(employee); // Add the single employee to the list
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                employees = getAllEmployees(); // Fetch all employees if no ID is provided
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Set the employee list as a request attribute to forward to JSP
        request.setAttribute("employees", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employeeList.jsp");
        dispatcher.forward(request, response);
    }
}
