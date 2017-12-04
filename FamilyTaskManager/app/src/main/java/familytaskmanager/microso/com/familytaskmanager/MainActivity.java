package familytaskmanager.microso.com.familytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
// TODO: 2017-11-26  For some reason import below gives error. Investigate if time.
//import java.util.function.ToDoubleBiFunction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager vp_pages;
    PagerAdapter pagerAdapter;
    TabLayout tbl_pages;
    Family family;
    public ArrayList<User> users;
    public static final int TOOL_REQUEST_CODE = 1;
    public static final int TASK_ACTIVITY_REQ_CODE = 2;
    public static final int FRIDGE_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();





        //Start code for Tab Menu
        /*vp_pages= (ViewPager) findViewById(R.id.vp_pages);
        pagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        vp_pages.setAdapter(pagerAdapter);

        tbl_pages= (TabLayout) findViewById(R.id.tbl_pages);
        tbl_pages.setupWithViewPager(vp_pages);*/
        //End code for Tab Menu

        //Changing action bar title
        setTitle("Quick access");

        //Creating the family. Family will take care of loading all necessary info from database
        // TODO: 2017-11-26  I'm trying to create the family in clean, final way. Someone please check
        //So the best I came up with, is no user in constructor, but in onStart (of Family)
        //after loading all the users, if list is empty, we add one
        family = new Family(0); //Random id

        //End of creation of family

        //TODO: 2017-11-27 Remove this Toast at some point, just here to know when onCreate is called
        Toast.makeText(this, "MainActivity's onCreate called", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        family.onStartFamily();

        //Start code for Tab Menu

        //Delay needed because of asynchronous DB
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                family.populateTaskUsers();

                System.out.println("WASD - after having populated the Task");
                for (Task t : family.getActiveTasks()) {
                    System.out.println("WASD - Name : " + t.getTitle() + " - hasUser() " + t.hasUser() + " - hasCreator()" + t.hasCreator());
                }

                vp_pages= (ViewPager) findViewById(R.id.vp_pages);
                pagerAdapter = new FragmentAdapter(getSupportFragmentManager());
                vp_pages.setAdapter(pagerAdapter);

                tbl_pages= (TabLayout) findViewById(R.id.tbl_pages);
                tbl_pages.setupWithViewPager(vp_pages);
                System.out.println("RUN DONE In MainAct....123456");
                for(User u : family.getUsers()) {
                    System.out.println("WASDF - Printing the assniged tasks of " + u.getFname());
                    u.printAssggnedTasks();
                }
            }
        }, 1000);
        //End code for Tab Menu
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initSpinner();
                family.populateTaskUsers();//TODO might need to remove if not usefull
            }
        }, 3000);
    }

    public void initSpinner() {
        /* CODE FOR USER CHANGE SPINNER */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        UserChangeAdapter user_adapter = new UserChangeAdapter(this, family.getUsers(), this);
        user_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        View v = navigationView.getHeaderView(0);
        Spinner user_spinner_list_view = (Spinner) v.findViewById(R.id.userMenuList);

        user_spinner_list_view.setAdapter(user_adapter);

        //Set the spinner selection to currentUser
        for(int i=0; i<family.getUsers().size(); i++){
            if(family.getCurrentUser().getId().equals(family.getUser(i).getId())){
                user_spinner_list_view.setSelection(i);
                break;
            }
        }
        /* END USER CHANGE */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shopping) {
            Toast.makeText(this, "Shopping", Toast.LENGTH_SHORT).show();
            vp_pages.setCurrentItem(0, true);
        } else if (id == R.id.nav_tasks) {
            Toast.makeText(this, "Tasks", Toast.LENGTH_SHORT).show();
            vp_pages.setCurrentItem(1, true);
        } else if (id == R.id.nav_people) {
            Toast.makeText(this, "People", Toast.LENGTH_SHORT).show();
            vp_pages.setCurrentItem(2, true);
        } else if (id == R.id.nav_schedule) {
            Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
            intent.putExtra("tasks", (Serializable) getFamilyActiveTaskList());
            startActivity(intent);
        } else if (id == R.id.nav_fridge) {
            Intent intent = new Intent(getApplicationContext(), FridgeActivity.class);
            intent.putExtra("fridge", (Serializable) getFamilyFridgeList());
            startActivityForResult(intent, FRIDGE_REQUEST_CODE);
        } else if (id == R.id.nav_tools) {
            Intent intent = new Intent(getApplicationContext(), ToolActivity.class);
            intent.putExtra("tools", (Serializable) getFamilyToolList());
            startActivityForResult(intent, TOOL_REQUEST_CODE);
        } else  if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings is not implemented", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // doesn't detect the return of my tool Activity TODO
        super.onActivityResult(requestCode, resultCode, data);
        //TODO a switch statement might be better
        if (requestCode == TOOL_REQUEST_CODE) {
            if (data.hasExtra("addedTools")) { // for my specific tool treatment.
                List<Tool> newTools = (List<Tool>) data.getSerializableExtra("addedTools");
                for (Tool t : newTools) {
                    if (requestToolCreation(t)) {
                        Toast.makeText(this, t.getName() + " has been added to your tools.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (data.hasExtra("deletedTools")) {
                List<String> oldTools = (List<String>) data.getSerializableExtra("deletedTools");
                for (String tID : oldTools) {
                    if (requestToolDeletion(tID)) {
                        Toast.makeText(this, "This tool has been deleted.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else if (requestCode == TASK_ACTIVITY_REQ_CODE) {
            if(data.hasExtra("updatedTask")) {
                System.out.println("passed the hasExtra xyz"); //TODO remove
                Task updatedTask = (Task) data.getSerializableExtra("updatedTask");
                System.out.println("WASD - When coming back from TaskDetail, hasUser = " + updatedTask.hasUser());
                family.updateTask(updatedTask);
            }
            if(data.hasExtra("updatedUser")) {
                User updatedUser = (User) data.getSerializableExtra("updatedUser");
                family.updateUser(updatedUser);
            }
            if(data.hasExtra("oldUser")) {
                User updatedUser = (User) data.getSerializableExtra("oldUser");
                family.updateUser(updatedUser);
            }
            if (data.hasExtra("deletedTask")) {
                // TODO maybe implements a if that check if family.getCurrentUser().itIsParent() pourrait
                // nous permettre de controller la suppression de task si on
                // TODO est un enfant. (Puisqu'on peut juste supprimer une task si le current user est un parent.

                Task deletedTask = (Task) data.getSerializableExtra("deletedTask");
                boolean completeThisTask = (boolean) data.getBooleanExtra("completeTask", false);
                String actionOnTask = (String) data.getStringExtra("action");
                if (requestTaskDeletion(deletedTask.getId(), completeThisTask)){
                    Toast.makeText(this, "This task has been "+ actionOnTask +".", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == FRIDGE_REQUEST_CODE) {
            if (data.hasExtra("addedItems")) {
                List<ShoppingItem> newItems = (List<ShoppingItem>) data.getSerializableExtra("addedItems");
                for (ShoppingItem t : newItems) {
                    if (requestFridgeItemCreation(t)) {
                        Toast.makeText(this, t.getName() + " has been added to your fridge.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (data.hasExtra("deletedItems")) {
                List<String> oldItems = (List<String>) data.getSerializableExtra("deletedItems");
                for (String ID : oldItems) {
                    requestFridgeItemDelete(ID);
                }
            }
        }
    }

    public void switchDrawerItem(int item) {

    }

    public List<ShoppingItem> getFamilyShoppingItems() { return family.getShoppingItems(); }

    public List<Task> getFamilyActiveTaskList() {
        return family.getActiveTasks();
    }

    public List<Task> getFamilyInactiveTaskList() {
        return family.getInactiveTasks();
    }

    public List<Tool> getFamilyToolList() { return family.getTools(); }

    public List<ShoppingItem> getFamilyFridgeList() { return family.getFridge(); }

    public List<User> getFamilyUserList() { return family.getUsers(); }

    /**
     * This method arranges the number pickers for date to have appropriate up and down limits.
     * If using this method, make sure the view you pass contains NumberPickers with the right id.
     *
     * @param view
     *          The view in which the NumberPicker are located
     */
    public static void setNumberPickersDialog(View view) {
        NumberPicker year = (NumberPicker) view.findViewById(R.id.dialogYearPicker);
        year.setMinValue(2017);
        year.setMaxValue(2020);
        NumberPicker month = (NumberPicker) view.findViewById(R.id.dialogMonthPicker);
        month.setMinValue(1);
        month.setMaxValue(12);
        NumberPicker day = (NumberPicker) view.findViewById(R.id.dialogDayPicker);
        day.setMinValue(1);
        day.setMaxValue(31);
    }

    public List<User> getFamilyPeopleList() {
        return family.getUsers();
    }

    /**
     * This method asks the family to create a new tasks, also giving the present user
     * Might return null if taask of same name exist already
     * TODO change the prensent user. Especially if it's now in Family
     * @param taskName
     * @param validTime
     * @param year
     * @param month
     * @param day
     * @param validReward
     * @param taskNote
     * @return
     */
    public Task requestTaskCreation(String taskName, double validTime, int year, int month,
                                    int day, int validReward, String taskNote) {

        //TODO Remove this dummy user and replace by currentUser
        //User creator = new User("1", "C.U.", "Creator Dummy", true, "menu_people", 0);
        User creator = family.getCurrentUser();

        System.out.println("Before creating task in MainActivity xyz, the creator is " + creator.getFname());
        Task created = family.requestTaskCreation(creator, taskName, validTime, year, month, day,
                validReward, taskNote);

        System.out.println("After creating task in MainActivity xyz, task creator name --> " + created.getCreator().getFname());

        return created;

    }

    public boolean requestShoppingItemCreation(ShoppingItem aShoppingItem) { return family.requestShoppingItemCreation(aShoppingItem); }
    public boolean requestShoppingItemDeletion(ShoppingItem aShoppingItem) { return family.requestShoppingItemDelete(aShoppingItem); }
    /**
     * Ask Family to update the task given in argument
     * @param atask
     * @return
     */
    public boolean requestTaskUpdate(Task atask) {
        return family.updateTask(atask);
    }
    public boolean requestUserUpdate(User aUser) {
        return family.updateUser(aUser);
    }
    public boolean requestToolCreation(Tool newTool) { return family.requestToolCreation(newTool); }
    public boolean requestToolDeletion(String oldTool) { return family.requestToolDelete(oldTool); }
    public boolean requestFridgeItemCreation(ShoppingItem newItem) { return family.requestFridgeItemCreation(newItem); }
    public boolean requestFridgeItemDelete(String oldItem) { return family.requestFridgeItemDelete(oldItem); }

    public boolean requestTaskDeletion(String oldTask, boolean completed) { return family.requestTaskDelete(oldTask, completed); }

    public boolean requestSetCurrentUser(int userIndex){
        return family.setCurrentUser(userIndex);
    }

    //TODO cheap method for a cheap breakfix, I need to fix this - walid
    public User getUserWithID(String id) { return family.getUserWithID(id); }
    public User requestCurrentUser() { return family.getCurrentUser(); }

    public User getCurrentUser(){
        return family.getCurrentUser();
    }

}
