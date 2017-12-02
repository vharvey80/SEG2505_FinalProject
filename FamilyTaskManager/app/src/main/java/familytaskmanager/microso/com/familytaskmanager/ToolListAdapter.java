package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince-PC on 2017-11-22.
 */

public class ToolListAdapter extends ArrayAdapter<Tool> {
    private final Context context;
    private final List<Tool> tools;
    private Tool selected_tool;

    public ToolListAdapter(Context context, List<Tool> values) {
        super(context, R.layout.tool_list_item, values);
        this.context = context;
        this.tools = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        selected_tool = tools.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tool_list_item, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.toolListItemName);
        TextView supplyView = (TextView) rowView.findViewById(R.id.toolListItemNbr);
        Button btnDelete = (Button) rowView.findViewById(R.id.delete);
        nameView.setText(selected_tool.getName());
        btnDelete.setTag(selected_tool);
        supplyView.setText(Integer.toString(selected_tool.getSupply()));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if (getContext() instanceof ToolActivity) {
                ((ToolActivity)getContext()).deleteTools((Tool) v.getTag());
            }
            }
        });
        return rowView;
    }
}
