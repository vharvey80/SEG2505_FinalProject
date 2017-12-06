package familytaskmanager.microso.com.familytaskmanager;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass, used for the shopping list
 * Created by Jean-Gabriel on 2017-11-23
 */
public class ShoppingFragment extends Fragment implements View.OnClickListener {

    private ListView toolsListview;
    private ListView groceriesListview;
    private ArrayList<ShoppingItem> groceries;
    private ArrayList<ShoppingItem> material;
    private ShoppingItemAdapter adapterGrocery;
    private ShoppingItemAdapter adapterTools;
    private ImageButton addShoppingItemButton;
    private Spinner spinnerCategory;
    private List<ShoppingItem> shoppingItems;
    private EditText name;


    public ShoppingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        //References
        addShoppingItemButton = (ImageButton) view.findViewById(R.id.addShopingItem);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinner_item);
        groceries = new ArrayList<ShoppingItem>();
        material = new ArrayList<ShoppingItem>();
        shoppingItems = ((MainActivity)getActivity()).getFamilyShoppingItems();
        name = (EditText) view.findViewById(R.id.shoppingItemName);

        addShoppingItemButton.setOnClickListener(this);

        //Looping in the list of shoppingItems  received from DB and adding them in the
        //corresponding list.
        for(ShoppingItem s : shoppingItems) {
            if(s.getCategory() == ShoppingItem.Category.Material){
                material.add(s);
            } else {
                groceries.add(s);
            }
        }

        groceriesListview = (ListView) view.findViewById(R.id.ListView_Groceries);
        toolsListview = (ListView) view.findViewById(R.id.ListView_Tools);

        //Creating an adapter for each lists and linking them to the corresponding one.
        adapterGrocery = new ShoppingItemAdapter(getActivity().getApplicationContext(), groceries, ShoppingFragment.this);
        adapterTools = new ShoppingItemAdapter(getActivity().getApplicationContext(), material, ShoppingFragment.this);
        groceriesListview.setAdapter(adapterGrocery);
        toolsListview.setAdapter(adapterTools);

        groceriesListview.setItemsCanFocus(false);
        toolsListview.setItemsCanFocus(false);

        groceriesListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        toolsListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        return view;
    }

    /**
     * Overrides the method OnClick, gets called when an item cretion is requested.
     *
     * @param v View from the activity
     */
    @Override
    public void onClick(View v) {
        String itemName = name.getText().toString().trim();
        ShoppingItem newItem;

        //Checking if the user has entered all the necessary information.
        if (!TextUtils.isEmpty(itemName)) {
            if (spinnerCategory.getSelectedItem().toString().equals("Material")) {
                newItem = new ShoppingItem("tempID", itemName, true, ShoppingItem.Category.Material);
                ((MainActivity) getActivity()).requestShoppingItemCreation(newItem);
                adapterTools.add(newItem);
            } else {
                newItem = new ShoppingItem("tempID", itemName, true, ShoppingItem.Category.Grocerie);
                ((MainActivity) getActivity()).requestShoppingItemCreation(newItem);
                adapterGrocery.add(newItem);
            }


            name.setText("");
        } else {
            Toast.makeText(getActivity(), "Please enter an item name", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper method to delete an item from the list, gets called when an item gets ticked.
     *
     * @param aShoppingItem Reference to the ticked shopping item
     */
    public void deleteShoppingItem(final ShoppingItem aShoppingItem) {
        Handler handler = new Handler();
        ((MainActivity) getActivity()).requestShoppingItemDeletion(aShoppingItem);

        //Added delay so it looks cleaner, and remove the item from the list
        if(aShoppingItem.getCategory() == ShoppingItem.Category.Material) {
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Item deleted", Toast.LENGTH_SHORT).show();
                    adapterTools.remove(aShoppingItem);
                }
            }, 750);
        } else {
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Item added to fridge", Toast.LENGTH_SHORT).show();

                    //If the item is set as a grocery item, we add it to the fridge.
                    aShoppingItem.setQuantity(1);
                    ((MainActivity) getActivity()).requestFridgeItemCreation(aShoppingItem);
                    adapterGrocery.remove(aShoppingItem);
                }
            }, 750);
        }
    }


}
