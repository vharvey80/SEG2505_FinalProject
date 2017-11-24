package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jean-gabriel on 11/22/2017.
 */

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {
    private final Context context;
    private final ArrayList<ShoppingItem> shoppingItems;

    public ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> values) {
        super(context, R.layout.checkboxlist_item, values);
        this.context = context;
        this.shoppingItems = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.checkboxlist_item, parent, false);

        CheckedTextView taskName = (CheckedTextView) rowView.findViewById(R.id.checkboxItem);

        ShoppingItem item = shoppingItems.get(position);

        taskName.setText(item.getName());

        return rowView;

    }
}
