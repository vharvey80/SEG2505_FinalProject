package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walid on 2017-11-18.
 */

public class TaskListAdapter extends ArrayAdapter {

    private final Context context;
    private final List<Task> values;

    public TaskListAdapter(Context context, List<Task> values) {
        super(context, R.layout.task_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_list_item, parent, false);

        TextView taskName = (TextView) rowView.findViewById(R.id.taskListItemName);
        TextView taskNote = (TextView) rowView.findViewById(R.id.tasksListItemNote);
        ImageView taskIcon = (ImageView) rowView.findViewById(R.id.taskListItemIcon);

        Task task = values.get(position);

        taskName.setText(task.getTitle());

        //This is only place holder logic. Once I get UI done, make this fancier
        if (task.hasNote()) {
            taskNote.setText(task.getNote());
        } else {
            taskNote.setText("The is no note");
        }

        if (task.hasUser()) {
            taskIcon.setImageResource(task.getUser().getProfilePicId());
        } else {
            taskIcon.setImageResource(android.R.drawable.ic_menu_add);
        }

        return rowView;
    }

}
