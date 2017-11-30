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
    private List<Task> tasks;

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
        //pictureID = aPictureID;
        tasks = new ArrayList<Task>();
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

    /*public boolean setPictureID(int aPictureID) {
        boolean wasSet = false;
        //pictureID = aPictureID;
        wasSet = true;
        return wasSet;
    }*/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSupply() {
        return supply;
    }

    /*public int getPictureID() {
        return pictureID;
    }*/

    public Task getTask(int index) {
        Task aTask = tasks.get(index);
        return aTask;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int numberOfTasks() {
        int number = tasks.size();
        return number;
    }

    public boolean hasTasks() {
        boolean has = tasks.size() > 0;
        return has;
    }

    public int indexOfTask(Task aTask) {
        int index = tasks.indexOf(aTask);
        return index;
    }

    public static int minimumNumberOfTasks() {
        return 0;
    }

    public boolean addTask(Task aTask) {
        boolean wasAdded = false;
        if (tasks.contains(aTask)) {
            return false;
        }
        tasks.add(aTask);
        if (aTask.indexOfTool(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aTask.addTool(this);
            if (!wasAdded) {
                tasks.remove(aTask);
            }
        }
        return wasAdded;
    }

    public boolean removeTask(Task aTask) {
        boolean wasRemoved = false;
        if (!tasks.contains(aTask)) {
            return wasRemoved;
        }

        int oldIndex = tasks.indexOf(aTask);
        tasks.remove(oldIndex);
        if (aTask.indexOfTool(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aTask.removeTool(this);
            if (!wasRemoved) {
                tasks.add(oldIndex, aTask);
            }
        }
        return wasRemoved;
    }

    public boolean addTaskAt(Task aTask, int index) {
        boolean wasAdded = false;
        if (addTask(aTask)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfTasks()) {
                index = numberOfTasks() - 1;
            }
            tasks.remove(aTask);
            tasks.add(index, aTask);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveTaskAt(Task aTask, int index) {
        boolean wasAdded = false;
        if (tasks.contains(aTask)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfTasks()) {
                index = numberOfTasks() - 1;
            }
            tasks.remove(aTask);
            tasks.add(index, aTask);
            wasAdded = true;
        } else {
            wasAdded = addTaskAt(aTask, index);
        }
        return wasAdded;
    }

    public ArrayList<Task> delete() {
        ArrayList<Task> copyOfTasks = new ArrayList<Task>(tasks);
        tasks.clear();
        for (Task aTask : copyOfTasks) {
            aTask.removeTool(this);
        }
        return copyOfTasks;
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "," +
                "name" + ":" + getName() + "," +
                "supply" + ":" + getSupply() + "," /*+
                "pictureID" + ":" + getPictureID()*/ + "]";
    }
}