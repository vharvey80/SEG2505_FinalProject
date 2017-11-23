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

import java.util.ArrayList;

/**
 * Created by Vince-PC on 2017-11-22.
 */

public class ToolListAdapter extends ArrayAdapter<Tool> {
    private final Context context;
    private final ArrayList<Tool> tools;
    private Tool selected_tool;

    public ToolListAdapter(Context context, ArrayList<Tool> values) {
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
        TextView supplyView = (TextView) rowView.findViewById(R.id.toolListItemSupply);
        Button btnDelete = (Button) rowView.findViewById(R.id.delete);
        nameView.setText(selected_tool.getName());
        supplyView.setText(supplyView.getText() + Integer.toString(selected_tool.getSupply())); // Look out for the text increment.
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*
                   Je suis pas capable d'aller chercher quel bouton a été cliquer. En sachant quel bouton a été cliquer,
                   nous serions capable de savoir quel item to delete. En sachant quel item to delete on pourrait
                   l'envoyer dans la fonction areYouSure(Tool toolToDelete).
                 */
                areYouSure(selected_tool); // Selected tool is not the one we're looking for.
            }
        });
        return rowView;
    }

    private void areYouSure(Tool toolToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Are you sure you want to delete this tool ? ");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}