package familytaskmanager.microso.com.familytaskmanager;

/**
 * Created by jean-gabriel on 11/23/2017.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grocerie implements Serializable {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Grocerie Attributes
    private int id;
    private String name;
    private int quantity;
    //private int pictureID;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Grocerie(int aId, String aName, int aQuantity) {
        id = aId;
        name = aName;
        quantity = aQuantity;
        //pictureID = aPictureID;\
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

    /*public boolean setPictureID(int aPictureID) {
        boolean wasSet = false;
        //pictureID = aPictureID;
        wasSet = true;
        return wasSet;
    }*/

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    /*public int getPictureID() {
        return pictureID;
    }*/


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "," +
                "name" + ":" + getName() + "," +
                "supply" + ":" + getQuantity() + "," /*+
                "pictureID" + ":" + getPictureID()*/ + "]";
    }
}