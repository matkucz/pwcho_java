import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Zadanie {
    public static void main(String [] args) throws SQLException{
        String JDBC = "com.mysql.jdbc.Driver";
        String URL = "jdbc:mysql://10.0.10.3:3306/cars?characterEncoding=utf8";
        String USER = "mkuczynski";
        String PASSWORD = "password";
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        try {
            while(conn == null) {
                try {
                    System.out.println("Lacze sie..");
                    conn = DriverManager.getConnection(URL, USER, PASSWORD);
                } catch (SQLException e) {
                    System.out.println("Wystapil blad");
                    System.out.println(e);
                }
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
		if (conn != null) {
		    System.out.println("Polaczono");
		    String brand = null;
            String model = null;
		    int carID = 0;
		    while(option != 5) {
		        showMenu();
		        option = scanner.nextInt();
		        switch(option) {
		            case 1:
		                getCars(conn);
		            break;                    
		            case 2:
                        scanner.nextLine();
		                System.out.println("Podaj marke: ");
		                brand = scanner.nextLine();
		                System.out.println("Podaj model: ");
		                model = scanner.nextLine();
		                insertCar(conn, brand, model);
		            break;
		            case 3:
		                System.out.println("Podaj id:");
		                carID = scanner.nextInt();
                        scanner.nextLine();
		                System.out.println("Podaj marke: ");
		                brand = scanner.nextLine();
		                System.out.println("Podaj model: ");
		                model = scanner.nextLine();
		                updateCar(conn, carID, brand, model);                        
		            break;
		            case 4:
		                System.out.println("Podaj id:");
		                carID = scanner.nextInt();
		                deleteCar(conn, carID);
		            break;
		            case 5:
                        conn.close();
		                System.exit(0);
		            break;
		            default:
		            break;
		        }
		    }
		}
        } catch (Exception e) {
            System.out.println(e);
        }
        conn.close();
    }



    public static void updateCar(Connection conn, int carID, String brand, String model) throws SQLException {
        String sql = "UPDATE cars SET brand = ?, model = ? WHERE ID = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, brand);
        statement.setString(2, model);
        statement.setInt(3, carID);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Samochod o id=" + carID + " zmodyfikowano pomyslnie.");
        }
        statement.close();
    }

    public static void insertCar(Connection conn, String brand, String model) throws SQLException {
        String sql = "INSERT INTO cars(brand, model) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, brand);
        statement.setString(2, model);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Samochod dodano pomyslnie.");
        }
        statement.close();
    }

    public static void getCars(Connection conn) throws SQLException {
        String sql = "SELECT * FROM cars";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            String brandName = result.getString("BRAND");
            String modelName = result.getString("MODEL");
            int carID = result.getInt("ID");
            System.out.println("ID: " + carID + ", BRAND: " + brandName + ", MODEL: " + modelName);
        }
        result.close();
    }



    public static void deleteCar(Connection conn, int carID) throws SQLException {
        String sql = "DELETE FROM cars WHERE ID = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, carID);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Samochod o id=" + carID + " usunieto pomyslnie.");
        }
        statement.close();
    }

    public static void showMenu() {
        System.out.println();
        System.out.println("Menu wyboru:");
        System.out.println("1. Pokaz rekordy");
        System.out.println("2. Dodaj rekod");
        System.out.println("3. Modyfikuj rekord");
        System.out.println("4. Usun rekord");
        System.out.println("5. Wyjdz z programu");
        System.out.println();
    }
}