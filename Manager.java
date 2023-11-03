package CW1;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Manager {
    private ArrayList<companies> companyList;
    /**
     * Constructor for the `Manager` class.
     */
    public Manager() {
        companyList = new ArrayList<>();
    }
    /**
     * Read company details from a file and populate the `companyList`.
     *
     * @param filePath The path to the file containing company details.
     */
    public void readCompanyDetailsFromFile(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into individual data fields
                String[] data = line.split("\t");
                int compNum = Integer.parseInt(data[0]);
                Name compname = new Name(data[1], "");
                String product = data[2];
                String date = data[3];
                float[] sharePrices = new float[5];
                for (int i = 0; i < 5; i++) {
                    sharePrices[i] = Float.parseFloat(data[4 + i]);
                }

                // Create a new 'companies' object and add it to the list
                companies newCompany = new companies(compNum, compname, product, date, sharePrices);
                companyList.add(newCompany);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate ranks for the companies based on percentage changes in share prices.
     */
    public void calculateRanks() {
    	

        // Calculating the percentage change for each company and store it in a list
        ArrayList<CompanyPercentageChange> percentageChanges = new ArrayList<>();

        for (companies company : companyList) {
            // Calculate percentage change based on share prices sp1 and sp5
            float initialPrice = company.getSharePriceArray()[0];
            float finalPrice = company.getSharePriceArray()[4];
            float percentageChange = ((finalPrice - initialPrice) / initialPrice) * 100;
            
            percentageChanges.add(new CompanyPercentageChange(company, percentageChange));
        }

        // Sorting the companies based on percentage change in descending order
        Collections.sort(percentageChanges, Collections.reverseOrder(Comparator.comparingDouble(CompanyPercentageChange::getPercentageChange)));

        // Assigning ranks to companies based on their position in the sorted list
        for (int i = 0; i < percentageChanges.size(); i++) {
            percentageChanges.get(i).getCompany().setRank(i + 1);
        }
    }
    /**
     * Generate a report with details of all companies.
     */
    public void generateReport() {
        System.out.println("Company Details:");
        System.out.println("comp_num\tcomp_name\tproduct/services\tdate\tsp1\tsp2\tsp3\tsp4\tsp5\tavg_sp\tRank");
        for (companies company : companyList) {
            System.out.printf("%03d\t%20s\t%20s\t%s\t%.1f\t%.1f\t%.1f\t%.1f\t%.1f\t%.1f\t%d%n",
                company.getcompnum(),
                company.getFirstName().getFirstName(),
                company.getProducts(),
                company.getdate(),
                company.getSharePriceArray()[0],
                company.getSharePriceArray()[1],
                company.getSharePriceArray()[2],
                company.getSharePriceArray()[3],
                company.getSharePriceArray()[4],
                company.getAverageShareprice(),
                company.getRank()
            );
        }
    }
    /**
     * Display full and short details of a specific company and write them to a file.
     *
     * @param companyNumber   The company number to display details for.
     * @param outputFilePath2 The path to the output file for writing details.
     */
    public void displayCompanyDetails(int companyNumber, String outputFilePath2) {
        // Find the company with the given company number
        companies selectedCompany = null;
        for (companies company : companyList) {
            if (company.getcompnum() == companyNumber) {
                selectedCompany = company;
                break;
            }
        }

        if (selectedCompany != null) {
            // bufferedwriter to write to the output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath2, true))) {
            	 System.out.println("Full details for " + String.format("%03d", companyNumber) + ":");
                 System.out.println(selectedCompany.getFullDetails());
                 System.out.println("Short details for " + String.format("%03d", companyNumber) + ":");
                 System.out.println(selectedCompany.getShortDetails());
                writer.newLine();
                writer.write("Full details for " + String.format("%03d", companyNumber) + ":");
                writer.newLine();
                writer.write(selectedCompany.getFullDetails());
                writer.newLine();

                writer.write("Short details for " + String.format("%03d", companyNumber) + ":");
                writer.newLine();
                writer.write(selectedCompany.getShortDetails());
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Company with number " + String.format("%03d", companyNumber) + " not found.");
        }
    }
    /**
     * Generate a report of top gainers and losers among the companies and write it to a file.
     *
     * @param outputFilePath The path to the output file for the report.
     */
    public void generateTopGainersAndLosersReport(String outputFilePath) {
        // Calculate the percentage change for each company and store it in a list
        ArrayList<CompanyPercentageChange> percentageChanges = new ArrayList<>();

        for (companies company : companyList) {
            // Calculate percentage change based on share prices sp1 and sp5
            float initialPrice = company.getSharePriceArray()[0];
            float finalPrice = company.getSharePriceArray()[4];
            float percentageChange = ((finalPrice - initialPrice) / initialPrice) * 100;

            percentageChanges.add(new CompanyPercentageChange(company, percentageChange));
        }

        // Sort the companies based on percentage change in descending order
        Collections.sort(percentageChanges, Collections.reverseOrder(Comparator.comparingDouble(CompanyPercentageChange::getPercentageChange)));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Create a method for generating a row with borders
            String border = generateBorder(11) + generateBorder(40) + generateBorder(40) +
                           generateBorder(11) + generateBorder(10) + generateBorder(10) +
                           generateBorder(10) + generateBorder(10) + generateBorder(10) +
                           generateBorder(10);
            // Generate the table header with borders
            String header = generateRowWithBorders("comp_num", "comp_name", "product/services", "date", "sp1", "sp2", "sp3", "sp4", "sp5", "avg_sp", "Rank", "min_sp", "max_sp");

            writer.write("Company Details:");
            writer.newLine();
            writer.write(header);
            writer.newLine();
            writer.write(border);
            writer.newLine();

            for (companies company : companyList) {
                // Format each row to have a consistent width and add borders
                String row = generateRowWithBorders(
                    Integer.toString(company.getcompnum()), company.getFirstName().getFirstName(), company.getProducts(), company.getdate(),
                    String.format("%.2f", company.getSharePriceArray()[0]), String.format("%.2f", company.getSharePriceArray()[1]),
                    String.format("%.2f", company.getSharePriceArray()[2]), String.format("%.2f", company.getSharePriceArray()[3]),
                    String.format("%.2f", company.getSharePriceArray()[4]), String.format("%.2f", company.getAverageShareprice()),
                    Integer.toString(company.getRank()),
                    String.format("%.2f", company.getMinSharePrice()), String.format("%.2f", company.getMaxSharePrice())
                );

                writer.write(row);
                writer.newLine();
                writer.write(border);
                writer.newLine();
            }
            writer.newLine();
            writer.newLine();
            writer.write("STATISTICAL REPORT");
            writer.newLine();
            writer.write("Top Gainers and Losers Report:");
            writer.newLine();
            writer.write("Company Name\tPercentage Change");
            writer.newLine();

            int topCount = 5; // Display the top 5 gainers
            for (int i = 0; i < topCount; i++) {
                CompanyPercentageChange companyChange = percentageChanges.get(i);
                writer.newLine();
                writer.write(companyChange.getCompany().getFirstName().getFirstName() + "\t" + companyChange.getPercentageChange() + "%");
                writer.write("\t (Gainer:)");
            }
            writer.newLine();

            int loserCount = 5; // Display the top 5 losers
            for (int i = percentageChanges.size() - 1; i >= percentageChanges.size() - loserCount; i--) {
                CompanyPercentageChange companyChange = percentageChanges.get(i);
                writer.newLine();
                writer.write(companyChange.getCompany().getFirstName().getFirstName() + "\t" + companyChange.getPercentageChange() + "%");
                writer.write("\t (Loser:)");
            }
            writer.newLine();

            // Find the company with the highest "max_sp" i.e with highest share price
            companies companyWithHighestMaxSP = companyList.stream()
                .max(Comparator.comparingDouble(c -> c.getMaxSharePrice()))
                .orElse(null);

            if (companyWithHighestMaxSP != null) {
                writer.newLine();
                writer.write("Company with Highest Share Price:");
                writer.newLine();
                writer.write(companyWithHighestMaxSP.getFullDetails());
                writer.newLine();
                writer.write("Short Details: " + companyWithHighestMaxSP.getShortDetails());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String generateBorder(int width) {
        StringBuilder border = new StringBuilder();
        for (int i = 0; i < width; i++) {
            border.append("-");
        }
        return border.toString();
    }

    private String generateRowWithBorders(String... columns) {
        StringBuilder row = new StringBuilder("|");
        for (String column : columns) {
            row.append(String.format("%-" + (columns.length == 1 ? 11 : 10) + "s|", column));
        }
        return row.toString();
    }
    
 // Other utility methods...

    /**
     * The `PasswordStore` class stores the authentication password for the Manager.
     */


    public class PasswordStore {
        private static final String PASSWORD = "6lowpan"; // Replace with your desired password
        /**
         * Check if the provided password matches the stored password.
         *
         * @param inputPassword The input password to check.
         * @return `true` if the input password matches the stored password, otherwise `false`.
         */
        public static boolean checkPassword(String inputPassword) {
            return PASSWORD.equals(inputPassword);
        }
    }
    /**
     * Authenticate the user by checking the password.
     *
     * @return `true` if authentication is successful, otherwise `false`.
     */
    public boolean authenticate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the password: ");
        String inputPassword = scanner.nextLine();

        return PasswordStore.checkPassword(inputPassword);
    }
    /**
     * The main method to run the Manager program.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Manager manager = new Manager();

        if (manager.authenticate()) {
            String filePath = "C:/Users/cex/Documents/companies.txt";
            manager.readCompanyDetailsFromFile(filePath);

            // Calculate ranks for all companies
            manager.calculateRanks();

            // Provide the output file path when calling generateTopGainersAndLosersReport
            String outputFilePath = "C:/Users/cex/Documents/gainers_and_losers.txt";
            manager.generateTopGainersAndLosersReport(outputFilePath);

            // Prompt the user for a company number and display details
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a company number: ");
            int companyNumber = scanner.nextInt();
            String outputFilePath2 = "C:/Users/cex/Documents/gainers_and_losers.txt"; // Specify your output file path
            manager.displayCompanyDetails(companyNumber, outputFilePath2);
        } else {
            System.out.println("Authentication failed. Exiting the program.");
        }
    }
    /**
     * Run the Manager program, including authentication, reading company details,
     * calculating ranks, and generating reports.
     */
    public void run() {
        if (!authenticate()) {
            System.out.println("Authentication failed. Access denied.");
            return;
        }

        // Authentication succeeded, perform tasks.
        readCompanyDetailsFromFile("C:/Users/cex/Documents/companies.txt");

        // Calculate ranks for all companies
        calculateRanks();

        generateReport();

        // Provide the output file path when calling generateTopGainersAndLosersReport
        String outputFilePath = "C:/Users/cex/Documents/gainers_and_losers.txt";
        generateTopGainersAndLosersReport(outputFilePath);
    }

    /**
     * Helper class to store company and its percentage change.
     */
    
    private class CompanyPercentageChange {
        private companies company;
        private float percentageChange;
        /**
         * Constructor for the `CompanyPercentageChange` class.
         *
         * @param company           The company associated with the percentage change.
         * @param percentageChange  The percentage change in share prices.
         */
        public CompanyPercentageChange(companies company, float percentageChange) {
            this.company = company;
            this.percentageChange = percentageChange;
        }
        /**
         * Get the associated company.
         *
         * @return The associated company.
         */
        public companies getCompany() {
            return company;
        }
        /**
         * Get the percentage change in share prices.
         *
         * @return The percentage change.
         */
        public float getPercentageChange() {
            return percentageChange;
        }
    }
}
