package familytaskmanager.microso.com.familytaskmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private List<Task> tasks;
    private List<Task> visibleTasks;
    private CalendarView calendar;
    private CalendarListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setTitle("Schedule");

        visibleTasks = new ArrayList<Task>();
        ListView listView = (ListView) findViewById(R.id.calendar_list_tasks);

        //Getting a reference of the calendar object.
        calendar = (CalendarView) findViewById(R.id.calendarView);

        //Unserializing the list of active tasks and storing in a local list.
        tasks = (List<Task>) getIntent().getSerializableExtra("tasks");

        //Creating a new adapter for our listView and assigning it.
        adapter = new CalendarListAdapter(this, visibleTasks);
        listView.setAdapter(adapter);

        //Creating a new listener for when the user changes day.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                visibleTasks.clear();

                //For all of the active tasks, check if they are on the date selected and add them
                //in the list showed.
                for(Task t : tasks) {
                    if(sameDay(year, month, day, t.getDueDate())) {
                        visibleTasks.add(t);
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    /**Helper function that returns if two dates are on the same day.
     *
     *@param year year of the first date to compare
     *@param month month of the first date to compare
     *@param day day of the first date to compare
     *@param date2 Date 2 to compare in Epoch Unix format
     *
     *@return Return true if the two dates are on the same day
     */
    public static boolean sameDay(int year, int month, int day, long date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        //Storing the two dates in a calendar instance to compare them.
        cal1.set(year, month, day);
        cal2.setTimeInMillis(date2);

        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
}
