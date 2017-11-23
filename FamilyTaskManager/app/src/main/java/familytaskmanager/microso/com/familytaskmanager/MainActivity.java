package familytaskmanager.microso.com.familytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager vp_pages;
    PagerAdapter pagerAdapter;
    TabLayout tbl_pages;
    Family family;

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

        //Start code for Tab Menu
        vp_pages= (ViewPager) findViewById(R.id.vp_pages);
        pagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        vp_pages.setAdapter(pagerAdapter);

        tbl_pages= (TabLayout) findViewById(R.id.tbl_pages);
        tbl_pages.setupWithViewPager(vp_pages);
        //End code for Tab Menu

        //Changing action bar title
        setTitle("Quick access");

        //Start of testing code. Getting a dummy family to test.
        family = Family.createDummyFamily();
        Toast.makeText(this, "FADSFDSAFADS", Toast.LENGTH_SHORT).show();
        //End ot testing code.

        //Test nav bar stuff
        View v = navigationView.getHeaderView(0);
        Spinner s = (Spinner) v.findViewById(R.id.userMenuList);

        UserSpinnerAdapter userSpinnerAdapter = new UserSpinnerAdapter(this.getApplicationContext(), family.getUsers());
        s.setAdapter(userSpinnerAdapter);
        //END

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
            startActivity(intent);
        } else  if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings is not implemented", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchDrawerItem(int item) {

    }

    public List<Task> getFamilyTaskList() {
        return family.getTasks();
    }

    public List<Tool> getFamilyToolList() { return family.getTools(); }

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

}
