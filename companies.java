package CW1;
/**
 * The `companies` class represents information about a company, including its
 * number, name, products, date, share prices, rank, and min/max share prices.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class companies {
    private int compnum;
    private Name compname;
    private String products; 
    private String date;
    private float[] sharePrices;
    private int rank;
    private float min_sp;
    private float max_sp;
    /**
     * Constructor for the `companies` class.
     *
     * @param compnum     The company number.
     * @param compname    The company name.
     * @param products    The products or services offered by the company.
     * @param date        The date associated with the company.
     * @param sharePrices An array of share prices for the company over five days.
     */

    public companies(int compnum, Name compname, String products, String date, float[] sharePrices) {
        this.compnum = compnum;
        this.compname = compname;
        this.products = products;
        this.date = date;
        this.sharePrices = sharePrices;
        this.min_sp = calculateMinSharePrice();
        this.max_sp = calculateMaxSharePrice();
    }
    /**
     * Set the rank of the company.
     *
     * @param rank The rank to set for the company.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getcompnum() {
        return compnum;
    }

    public Name getFirstName() {
        return compname;
    }

    public String getProducts() {
        return products;
    }

    public String getdate() {
        return date;
    }

    public float[] getSharePriceArray() {
        return sharePrices;
    }
    
    private float calculateMinSharePrice() {
        float minPrice = Float.MAX_VALUE;
        for (float price : sharePrices) {
            if (price < minPrice) {
                minPrice = price;
            }
        }
        return minPrice;
    }
    private float calculateMaxSharePrice() {
        float maxPrice = Float.MIN_VALUE;
        for (float price : sharePrices) {
            if (price > maxPrice) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }

    public double getAverageShareprice() {
        double total = 0;
        for (float price : sharePrices) {
            total += price;
        }
        double average = total / sharePrices.length;
        return average;
    }
    
    public int getRank() {
        return rank;
    }

    public float getMinSharePrice() {
        return min_sp;
    }

    public float getMaxSharePrice() {
        return max_sp;
    }
 // Other getter and utility methods...

    /**
     * Get a formatted string with full details about the company.
     *
     * @return A formatted string with full details.
     */
    public String getFullDetails() {
        return "Company Number is " + String.format("%03d", compnum) + ", named " + compname.getFirstName() +
                 " produces " + products +
                " Over the past five days, the share price has been: " + Arrays.toString(sharePrices) + "\n" +
                "Therefore, " + compname.getFirstName() + " had an average share price of " + getAverageShareprice() + ".";
    }
    /**
     * Get a formatted string with short details about the company.
     *
     * @return A formatted string with short details.
     */
    public String getShortDetails() {
        return "CN " + String.format("%03d", compnum) + " (" + compname.getFirstName().charAt(0) + ") has an average SP of " + getAverageShareprice();
    }
    /**
     * Create a new `companies` object based on the current object.
     *
     * @param compnum     The company number.
     * @param compname    The company name.
     * @param products    The products or services offered by the company.
     * @param date        The date associated with the company.
     * @param sharePrices An array of share prices for the company over five days.
     * @return A new `companies` object.
     */
    public companies newCompany(int compnum, Name compname, String products, String date, float[] sharePrices) {
        return new companies(compnum, compname, products, date, sharePrices);
    }
}
