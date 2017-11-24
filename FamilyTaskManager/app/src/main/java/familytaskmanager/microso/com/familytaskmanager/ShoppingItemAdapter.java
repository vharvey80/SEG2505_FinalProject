package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jean-gabriel on 11/22/2017.
 */

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {
    private final Context context;
    private final ArrayList<ShoppingItem> shoppingItems;
    private ShoppingItem selected_item;

    public ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> values) {
        super(context, R.layout.checkboxlist_item, values);
        this.context = context;
        this.shoppingItems = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        selected_item = shoppingItems.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View rowView = inflater.inflate(R.layout.checkboxlist_item, parent, false);
        TextView nameView = (TextView) convertView.findViewById(R.id.checkedTextView1);
        nameView.setText(selected_item.getName());
        return convertView;
    }
}
