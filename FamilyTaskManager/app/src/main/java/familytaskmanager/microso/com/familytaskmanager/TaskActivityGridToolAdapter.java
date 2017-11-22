package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by walid on 2017-11-20.
 */

public class TaskActivityGridToolAdapter extends ArrayAdapter {

    private final Context context;
    private final List<Tool> values;

    public TaskActivityGridToolAdapter(Context context, List<Tool> values) {
        super(context, R.layout.task_detail_tool_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_detail_tool_item, parent, false);

        TextView toolName = (TextView) rowView.findViewById(R.id.toolGridItemName);
        ImageView toolIcon = (ImageView) rowView.findViewById(R.id.toolGridItemIcon);

        Tool tool = values.get(position);
        if(tool == null) {
            tool = new Tool(-1, "No tool needed", 0);
        }

        toolName.setText(tool.getName());
        //toolIcon.setImageResource(tool.getPictureID());

        return rowView;
    }

}
