package familytaskmanager.microso.com.familytaskmanager;

import java.io.Serializable;
import java.util.*;

public class Tool implements Serializable {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Tool Attributes
    private String id;
    private String name;
    private int supply;

    //Tool Associations
        // No associations needed.

    //------------------------
    // CONSTRUCTOR
    //------------------------
    public Tool() {
        // For Firebase
    }

    public Tool(String aId, String aName, int aSupply) {
        id = aId;
        name = aName;
        supply = aSupply;
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

    public boolean setSupply(int aSupply) {
        boolean wasSet = false;
        supply = aSupply;
        wasSet = true;
        return wasSet;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSupply() {
        return supply;
    }

    public String toString() {
        return name + " | " + supply;
    }
}