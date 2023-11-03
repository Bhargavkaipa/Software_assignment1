package CW1;

import java.util.Arrays;
/**
 * The `saicompanies` class demonstrates the usage of the `companies` class by
 * creating instances of companies and displaying their details.
 */
public class saicompanies {
    public static void main(String[] args) {
        float[] fresSharePrices = {549, 533, 532, 529,525};
        float[] kgfSharePrices = {204,203,201,202,204};
        float[] sgroSharePrices = {690,679,685,695,701};
        float[] hlSharePrices = {705,698,690,695,700};
        float[] btSharePrices = {110,111,112,113,116};
        Name fresName = new Name("Fresnillo", "");
        companies fres = new companies(1, fresName, "gold", "14 May 2008", fresSharePrices);

        System.out.println(fres.getFullDetails());
        System.out.println(fres.getShortDetails());
        System.out.println(" ");

        Name kgfName = new Name("Kingfisher", "");
        companies kgf = fres.newCompany(2, kgfName, "Home Improvement Retailers", "24 November 1982", kgfSharePrices);
        System.out.println(kgf.getFullDetails());
        System.out.println(kgf.getShortDetails());
        System.out.println(" ");

        Name sgroName = new Name("Segro", "");
        companies sgro = fres.newCompany(3, sgroName, "Real Estate Investment Trusts", "01 Dec 1949", sgroSharePrices);
        System.out.println(sgro.getFullDetails());
        System.out.println(sgro.getShortDetails());
        System.out.println(" ");

        Name hlName = new Name("Hargreaves Lansdown", "");
        companies hl = fres.newCompany(4, hlName,"Investment Banking and Brokerage Services" , "18 May 2007", hlSharePrices);
        System.out.println(hl.getFullDetails());
        System.out.println(hl.getShortDetails());
        System.out.println(" ");

        Name btName = new Name("BT Group", "");
        companies bt = fres.newCompany(5, btName, "Telecommunications Services",  "03 December 1984", btSharePrices);
        System.out.println(bt.getFullDetails());
        System.out.println(bt.getShortDetails());
    }
}
/**
 * The `Manager` class manages a list of companies, reads company details from a
 * file, calculates ranks, generates reports, and performs various operations.
 */
