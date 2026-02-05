import java.util.Scanner;

public class AuctionApp {
    private static final Scanner SCANNER = new Scanner(System.in);

    private static class Vehicle {
        String regNo;
        double costPrice;
        double deposits;
        double expenses;

        double balance(double salePrice) {
            return Math.max(0, salePrice - deposits);
        }

        double profit(double salePrice) {
            return salePrice - costPrice - expenses;
        }
    }

    private static double promptDouble(String label) {
        while (true) {
            System.out.print(label + ": ");
            if (SCANNER.hasNextDouble()) {
                return SCANNER.nextDouble();
            }
            SCANNER.next();
            System.out.println("Enter a numeric value.");
        }
    }

    private static Vehicle captureVehicle() {
        Vehicle v = new Vehicle();
        System.out.print("Enter registration number: ");
        v.regNo = SCANNER.next();
        v.costPrice = promptDouble("Enter vehicle cost");
        v.expenses = promptDouble("Enter total expenses so far");
        v.deposits = promptDouble("Enter deposits received so far");
        return v;
    }

    private static double[] captureBids() {
        double[] bids = new double[3];
        for (int i = 0; i < 3; i++) {
            bids[i] = promptDouble("Bidder " + (i + 1) + " bid amount");
        }
        return bids;
    }

    private static int highestBidder(double[] bids) {
        int idx = 0;
        for (int i = 1; i < bids.length; i++) {
            if (bids[i] > bids[idx]) idx = i;
        }
        return idx;
    }

    public static void main(String[] args) {
        System.out.println("--- Vehicle Auction ---");
        Vehicle vehicle = captureVehicle();
        double[] bids = captureBids();
        int winner = highestBidder(bids);
        double salePrice = bids[winner];
        double balance = vehicle.balance(salePrice);
        double profit = vehicle.profit(salePrice);

        System.out.println("\n--- Results ---");
        System.out.printf("Vehicle: %s%n", vehicle.regNo);
        System.out.printf("Cost: %.2f | Expenses: %.2f | Deposits: %.2f%n", vehicle.costPrice, vehicle.expenses, vehicle.deposits);
        System.out.printf("Highest Bidder: %d | Sale Price: %.2f%n", winner + 1, salePrice);
        System.out.printf("Balance due: %.2f%n", balance);
        System.out.printf("Profit/Loss: %.2f%n", profit);
        if (profit > 0) {
            System.out.println("Status: Profit");
        } else if (profit < 0) {
            System.out.println("Status: Loss");
        } else {
            System.out.println("Status: Break-even");
        }
    }
}
