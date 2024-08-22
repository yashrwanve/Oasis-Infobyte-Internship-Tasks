package oasis.task1;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CancellationSystem {

    // Method to cancel a reservation (now centralized)
    public static void cancelReservation(String pnrNumber) {
        String query = "DELETE FROM Reservations WHERE pnr_number = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pnrNumber);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reservation cancelled successfully!");
            } else {
                System.out.println("No reservation found with that PNR number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("PNR Number: ");
        String pnrNumber = scanner.nextLine();
        cancelReservation(pnrNumber);
        scanner.close();
    }
}
