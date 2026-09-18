package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

public class ExtendedWarranty extends Warranty {

    private String accidentalDamage;
    private double additionalCost; // Additional cost for the extended warranty

    public ExtendedWarranty(String accidentalDamage, double additionalCost, String fabricDefects, int duration, String priceProduct, String id) {
        super(fabricDefects, duration, priceProduct, id);
        this.accidentalDamage = accidentalDamage;
        this.additionalCost = additionalCost;
    }

    public String getAccidentalDamage() {
        return accidentalDamage;
    }

    public void setAccidentalDamage(String accidentalDamage) {
        this.accidentalDamage = accidentalDamage;
    }

    public double getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(double additionalCost) {
        this.additionalCost = 0.10 * Double.parseDouble(getPriceProduct());
    }

}
