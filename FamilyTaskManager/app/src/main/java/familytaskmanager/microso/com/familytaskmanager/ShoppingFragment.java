package familytaskmanager.microso.com.familytaskmanager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Created by Jean-Gabriel on 2017-11-23
 */
public class ShoppingFragment extends Fragment {

    ListView toolsListview;
    ListView groceriesListview;
    ArrayList<ShoppingItem> groceries = new ArrayList<ShoppingItem>();
    ArrayList<ShoppingItem> tools = new ArrayList<ShoppingItem>();
    ShoppingItemAdapter adapterGrocery;
    ShoppingItemAdapter adapterTools;
    Context context;

    public ShoppingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        tools.add(new ShoppingItem(1, "Bucket", 5, true, ShoppingItem.Category.Material));
        tools.add(new ShoppingItem(2, "Broom", 5, true, ShoppingItem.Category.Material));
        tools.add(new ShoppingItem(3, "Wrench", 5, true, ShoppingItem.Category.Material));
        tools.add(new ShoppingItem(4, "Screwdriver", 5, true, ShoppingItem.Category.Material));
        tools.add(new ShoppingItem(5, "Planks", 5, true, ShoppingItem.Category.Material));

        groceries.add(new ShoppingItem(1, "Butter", 1, true, ShoppingItem.Category.Grocerie));
        groceries.add(new ShoppingItem(1, "Chips", 2, true, ShoppingItem.Category.Grocerie));

        groceriesListview = (ListView) view.findViewById(R.id.ListView_Groceries);
        toolsListview = (ListView) view.findViewById(R.id.ListView_Tools);

        adapterGrocery = new ShoppingItemAdapter(getActivity().getApplicationContext(), groceries);
        adapterTools = new ShoppingItemAdapter(getActivity().getApplicationContext(), tools);

        groceriesListview.setAdapter(adapterGrocery);
        toolsListview.setAdapter(adapterTools);

        groceriesListview.setItemsCanFocus(false);
        toolsListview.setItemsCanFocus(false);

        groceriesListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        toolsListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        return view;
    }


}
