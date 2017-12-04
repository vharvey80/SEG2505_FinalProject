package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jean-Gabriel on 11/23/2017.
 */

public class FridgeListAdapter extends ArrayAdapter<ShoppingItem> {
    private final Context context;
    private final List<ShoppingItem> groceries;
    private ShoppingItem selected_grocerie;

    public FridgeListAdapter(Context context, List<ShoppingItem> values) {
        super(context, R.layout.grocerie_list_item, values);
        this.context = context;
        this.groceries = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        selected_grocerie = groceries.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.grocerie_list_item, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.grocerieListItemName);
        TextView supplyView = (TextView) rowView.findViewById(R.id.grocerieListItemNbr);
        Button btnDelete = (Button) rowView.findViewById(R.id.delete);
        nameView.setText(selected_grocerie.getName());
        btnDelete.setTag(selected_grocerie);
        supplyView.setText(Integer.toString(selected_grocerie.getQuantity()));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getContext() instanceof FridgeActivity) {
                    ((FridgeActivity)getContext()).deleteItem((ShoppingItem) v.getTag());
                }
            }
        });
        return rowView;
    }
}
