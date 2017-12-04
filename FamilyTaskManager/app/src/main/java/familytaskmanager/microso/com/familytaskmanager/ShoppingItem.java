package familytaskmanager.microso.com.familytaskmanager;


import java.io.Serializable;

public class ShoppingItem implements Serializable{

    //------------------------
    // ENUMERATIONS
    //------------------------

    public enum Category {Material, Grocerie}

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //ShoppingItem Attributes
    private String id;
    private String name;
    private boolean needToBuy;
    private Category category;
    private int quantity;

    //------------------------
    // CONSTRUCTOR
    //------------------------
    public ShoppingItem() {
        // For Firebase.
    }

    public ShoppingItem(String aId, String aName, boolean aNeedToBuy, Category aCategory) {
        id = aId;
        name = aName;
        needToBuy = aNeedToBuy;
        category = aCategory;
    }

    public ShoppingItem(String aId, String aName, boolean aNeedToBuy, Category aCategory, int aQuantity) {
        id = aId;
        name = aName;
        needToBuy = aNeedToBuy;
        category = aCategory;
        quantity = aQuantity;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setId(String aId) {
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity(){ return quantity; }

    public boolean getNeedToBuy() {
        return needToBuy;
    }

    public Category getCategory() {
        return category;
    }

//    public boolean isNeedToBuy() {
//        return needToBuy;
//    }

    public void delete() {
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "," +
                "name" + ":" + getName() + "," +
                "needToBuy" + ":" + getNeedToBuy() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "category" + "=" + (getCategory() != null ? !getCategory().equals(this) ? getCategory().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}