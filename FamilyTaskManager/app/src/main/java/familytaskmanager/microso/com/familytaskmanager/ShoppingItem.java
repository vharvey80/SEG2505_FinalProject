package familytaskmanager.microso.com.familytaskmanager;


public class ShoppingItem {

    //------------------------
    // ENUMERATIONS
    //------------------------

    public enum Category {Material, Grocerie}

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //ShoppingItem Attributes
    private int id;
    private String name;
    private int quantity;
    private boolean needToBuy;
    private Category category;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public ShoppingItem(int aId, String aName, int aQuantity, boolean aNeedToBuy, Category aCategory) {
        id = aId;
        name = aName;
        quantity = aQuantity;
        needToBuy = aNeedToBuy;
        category = aCategory;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setId(int aId) {
        boolean wasSet = false;
        id = aId;
        wasSet = true;
        return wasSet;
    }

    public boolean setName(String aName) {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setQuantity(int aQuantity) {
        boolean wasSet = false;
        quantity = aQuantity;
        wasSet = true;
        return wasSet;
    }

    public boolean setNeedToBuy(boolean aNeedToBuy) {
        boolean wasSet = false;
        needToBuy = aNeedToBuy;
        wasSet = true;
        return wasSet;
    }

    public boolean setCategory(Category aCategory) {
        boolean wasSet = false;
        category = aCategory;
        wasSet = true;
        return wasSet;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getNeedToBuy() {
        return needToBuy;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isNeedToBuy() {
        return needToBuy;
    }

    public void delete() {
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "," +
                "name" + ":" + getName() + "," +
                "quantity" + ":" + getQuantity() + "," +
                "needToBuy" + ":" + getNeedToBuy() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "category" + "=" + (getCategory() != null ? !getCategory().equals(this) ? getCategory().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}