package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jean-Gabriel on 12/4/2017.
 */

public class CalendarListAdapter extends ArrayAdapter<Task> {
    private final Context context;
    private List<Task> values;

    public CalendarListAdapter(Context context, List<Task> values) {
        super(context, R.layout.taskcalendar_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.taskcalendar_list_item, parent, false);

        TextView taskName = (TextView) rowView.findViewById(R.id.taskListItemName);
        TextView taskUser = (TextView) rowView.findViewById(R.id.taskAssigned);

        Task task = values.get(position);

        taskName.setText(task.getTitle());

        //Get the information concerning the user and updates the UI
        if (task.hasUser()) {
            taskUser.setText("User: " + task.getUser().getFname());
        } else {
            taskUser.setText("No assigned user");
        }

        return rowView;
    }
}
