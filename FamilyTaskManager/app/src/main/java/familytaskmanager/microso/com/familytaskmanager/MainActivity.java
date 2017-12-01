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
    Family familyDB;
    public ArrayList<User> users;
    public static final int TOOL_REQUEST_CODE = 1;
    public static final int TASK_ACTIVITY_REQ_CODE = 2;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* CODE FOR USER CHANGE SPINNER */
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < 5; i++) {
            users.add(new User(Integer.toString(i + 1), "Fname_" + i, "Lname_" + i, true, "menu_people", (1 + i)));
        }

        UserChangeAdapter user_adapter = new UserChangeAdapter(this, users);
        user_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        View v = navigationView.getHeaderView(0);
        Spinner user_spinner_list_view = (Spinner) v.findViewById(R.id.userMenuList);

        user_spinner_list_view.setAdapter(user_adapter);
        /* END USER CHANGE */

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
                vp_pages= (ViewPager) findViewById(R.id.vp_pages);
                pagerAdapter = new FragmentAdapter(getSupportFragmentManager());
                vp_pages.setAdapter(pagerAdapter);

                tbl_pages= (TabLayout) findViewById(R.id.tbl_pages);
                tbl_pages.setupWithViewPager(vp_pages);
                System.out.println("RUN DONE In MainAct....123456");
            }
        }, 1000);

        //End code for Tab Menu
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
        } else if (id == R.id.nav_schedule) {
            Toast.makeText(this, "Schedule is not implemented", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_backlog) {
            Toast.makeText(this, "Backlog is not implemented", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_people) {
            Toast.makeText(this, "People", Toast.LENGTH_SHORT).show();
            vp_pages.setCurrentItem(2, true);
        } else if (id == R.id.nav_fridge) {
            Intent intent = new Intent(getApplicationContext(), FridgeActivity.class);
            startActivity(intent);
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
                    requestToolCreation(t);
                }
            }
            if (data.hasExtra("deletedTools")) {
                List<Tool> oldTools = (List<Tool>) data.getSerializableExtra("deletedTools");
                for (Tool t : oldTools) {
                    Toast.makeText(this, t.getId(), Toast.LENGTH_SHORT).show();
                    requestToolDeletion(t);
                }
            }
        } else if (requestCode == TASK_ACTIVITY_REQ_CODE) {
            System.out.println("Passed the else if xyz"); //TODO remove
            if(data.hasExtra("updatedTask")) {
                System.out.println("passed the hasExtra xyz"); //TODO remove
                Task updatedTask = (Task) data.getSerializableExtra("updatedTask");
                family.updateTask(updatedTask);
            }
        }
    }

    public void switchDrawerItem(int item) {

    }

    public List<Task> getFamilyActiveTaskList() {
        return family.getActiveTasks();
    }

    public List<Task> getFamilyInactiveTaskList() {
        return family.getInactiveTasks();
    }

    public List<Tool> getFamilyToolList() { return family.getTools(); }

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
        User creator = new User("1", "C.U.", "Creator Dummy", true, "menu_people", 0);

        Task created = family.requestTaskCreation(creator, taskName, validTime, year, month, day,
                validReward, taskNote);

        return created;

    }

    /**
     * Ask Family to update the task given in argument
     * @param atask
     * @return
     */
    public boolean requestTaskUpdate(Task atask) {
        return family.updateTask(atask);
    }
    public boolean requestToolCreation(Tool newTool) {
        return family.requestToolCreation(newTool);
    }
    public boolean requestToolDeletion(Tool oldTool) { return family.requestToolDelete(oldTool); }

}
