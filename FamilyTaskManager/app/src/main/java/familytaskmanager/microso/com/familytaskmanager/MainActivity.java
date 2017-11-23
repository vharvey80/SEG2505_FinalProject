package familytaskmanager.microso.com.familytaskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager vp_pages;
    PagerAdapter pagerAdapter;
    TabLayout tbl_pages;
    Family family;
    public ArrayList<User> users;

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

        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < 5; i++) {
            users.add(new User((i + 1), "Fname_" + i, "Lname_" + i, true, 1, 20));
        }

        UserChangeAdapter user_adapter = new UserChangeAdapter(this, users);
        user_adapter.setDropDownViewResource(R.layout.user_change_list_item);

        View v = navigationView.getHeaderView(0);
        Spinner user_spinner_list_view = (Spinner) v.findViewById(R.id.userMenuList);

        user_spinner_list_view.setAdapter(user_adapter);

        /*users = new ArrayList<User>();
        users.add(new User(1, "Vincent", "Harvey", true, 1, 19));
        users.add(new User(2, "Walid", "Bounouar", true, 1, 122));
        users.add(new User(3, "Thomas", "Charette", true, 1, 2));*/


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
        //End ot testing code.

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
}
