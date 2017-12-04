package familytaskmanager.microso.com.familytaskmanager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {


    TaskListAdapter taskListAdapter;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        //Setting onClick for the FAB
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addTaskFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskFabClicked();
            }
        });


        //List view code
        final ListView listView = (ListView) view.findViewById(R.id.tasksListView);

        taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(),
                ((MainActivity)getActivity()).getFamilyActiveTaskList() ,((MainActivity)getActivity()).getFamilyPeopleList(),
                getActivity());

        listView.setAdapter(taskListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                final Task clickedTask = (Task) parent.getItemAtPosition(position);

                //TODO this is just a cheap breakfix, I need to find cause of problem
                if((clickedTask.getCreator() == null) && (clickedTask.getCreatorID() != null)) {
                    User creator = ((MainActivity)getActivity()).getUserWithID(clickedTask.getCreatorID());
                    clickedTask.setCreator(creator);
                }

                Intent intent = new Intent(getActivity().getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("task", (Serializable) clickedTask);
                intent.putExtra("toolList", (Serializable) ((MainActivity)getActivity()).getFamilyToolList());
                intent.putExtra("userList", (Serializable) ((MainActivity)getActivity()).getFamilyPeopleList());
                intent.putExtra("currentUser", (Serializable) ((MainActivity)getActivity()).requestCurrentUser());
                getActivity().startActivityForResult(intent, MainActivity.TASK_ACTIVITY_REQ_CODE);

            }
        });
        //End of List view code

        //code foe switch
        final Switch taskSwitch = (Switch) view.findViewById(R.id.taskSwitch);
        taskSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //MainActivity

                if (isChecked) {
                    //This double getting of the "real" user is requiered because of issue with
                    //pulling from the DB. The current user seems to not be the same object as the
                    //one in the user list, even if the have same ID.
                    User currentUser = ((MainActivity) getActivity()).requestCurrentUser();
                    User realCurrent = ((MainActivity) getActivity()).getUserWithID(currentUser.getId());

                    List<Task> currentUserTasks = realCurrent.getAssignedTo();
                    TaskListAdapter newAdapter;
                    newAdapter = new TaskListAdapter(getActivity().getApplicationContext(),
                            currentUserTasks ,((MainActivity)getActivity()).getFamilyPeopleList(),
                            getActivity());
                    listView.setAdapter(newAdapter);
                    taskListAdapter.notifyDataSetChanged();
                } else {
                    //I never actually changed taskListAdapter, so can just re-assign to listView
                    listView.setAdapter(taskListAdapter);
                    taskListAdapter.notifyDataSetChanged();
                }
            }
        });
        //end code for switch
        return view;
    }

    public void taskFabClicked() {
        showDialogPart1();

    }

    /**
     * REMOVE METHOD, JUST FOR TEST.
     * @param list
     * @return
     */
    private String[] getToolArray(List<Tool> list) {
        String[] out = new String[list.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = list.get(i).getName();
        }
        return out;
    }

    private void showDialogPart1() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.dialog_change_or_create_task_1, null);
        MainActivity.setNumberPickersDialog(dialogView);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Task Info (1/2)");

        //If the user is not parent, lock reward at 0 pts
        if (!((MainActivity)getActivity()).requestCurrentUser().getIsParent()){
            dialogBuilder.setMessage("Since you're not Parent, reward is default 1.");
            EditText rewardInput = (EditText) dialogView.findViewById(R.id.dialogRewardField);
            rewardInput.setText("1");
            rewardInput.setEnabled(false);
        }


        //get buttons to set onClick
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.dialogConfirmAndNext);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.dialogCancel);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        //setting function of the confirm button
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting boolean to stop advancement if needed
                boolean allGood = true;

                //Here we get all the info entered on the first dialog
                EditText nameInput = (EditText) dialogView.findViewById(R.id.dialogTaskNameField);
                String taskName = nameInput.getText().toString();

                EditText timeInput = (EditText) dialogView.findViewById(R.id.dialogTimeField);
                String taskTime = timeInput.getText().toString();

                NumberPicker yearInput = (NumberPicker) dialogView.findViewById(R.id.dialogYearPicker);
                int year = yearInput.getValue();

                NumberPicker monthInput = (NumberPicker) dialogView.findViewById(R.id.dialogMonthPicker);
                int month = monthInput.getValue();

                NumberPicker dayInput = (NumberPicker) dialogView.findViewById(R.id.dialogDayPicker);
                int day = dayInput.getValue();

                EditText rewardInput = (EditText) dialogView.findViewById(R.id.dialogRewardField);
                String taskReward = rewardInput.getText().toString();

                EditText noteInput = (EditText) dialogView.findViewById(R.id.dialogNoteField);
                String taskNote = noteInput.getText().toString();
                //End of getting the info

                //Checking if any field is empty
                boolean allFilled = checkAllFilled(taskName, taskTime, taskReward, taskNote);
                if (!allFilled) {
                    Toast.makeText(getActivity(), "Please fill all fields.", Toast.LENGTH_LONG).show();
                    allGood = false;
                }

                //validating the rest. Only show message if all is good up to validation.
                // Avoiding to many messages to user
                double validTime = validateTime(taskTime); //validating input
                if (validTime == -1 && allGood) {
                    Toast.makeText(getActivity(), "Wrong Time input. Make sure its a number over 0.", Toast.LENGTH_LONG).show();
                    allGood = false;
                }

                boolean validDate = validateDate(year, month, day); //Validating date
                if (!validDate && allGood) {
                    Toast.makeText(getActivity(), "Wrong Date input. Make sure to respect the number " +
                            "of days per month and that you choose future date.", Toast.LENGTH_LONG).show();
                    allGood = false;
                }

                int validReward = validateReward(taskReward); //validating input
                if (validReward == -1 && allGood) {
                    Toast.makeText(getActivity(), "Wrong Reward input. Make sure its an integer over 0.", Toast.LENGTH_LONG).show();
                    allGood = false;
                }
                //End of validating fields

                if (allGood) {
                    dialog.cancel();
                    Task createdTask = ((MainActivity)getActivity()).requestTaskCreation(taskName,
                            validTime, year, month, day, validReward, taskNote);

                    if (createdTask == null) {
                        Toast.makeText(getActivity(), "A task of the same name exists already", Toast.LENGTH_LONG).show();
                    } else {
                        taskListAdapter.notifyDataSetChanged(); //need to update adapter after adding task
                        Toast.makeText(getActivity(), "Task Succesfully Created!", Toast.LENGTH_SHORT).show();
                        showDialogPart2(createdTask);
                    }
                }
            }
        });

        //cancel button function
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void showDialogPart2(final Task createdTask) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.dialog_change_or_create_task_2, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Task Info (2/2)");

        //get buttons to set onClick
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.dialogConfirmAndNext);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.dialogCancel);

        //Getting LIstView and populating
        final List<Tool> toolList = ((MainActivity)getActivity()).getFamilyToolList();
        final ListView list = (ListView) dialogView.findViewById(R.id.toolListView);
        String[] toolArray = this.getToolArray(toolList);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, toolArray);
        list.setAdapter(adapter);
        //End of listView code

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        //setting function of the confirm button
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = list.getCheckedItemPositions();

                for (int i = 0; i < list.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {

                        //TODO review strategie for adding tool
                        // To add tool, we will add them to the tool object given in argument to onclick
                        // and call Family method update User
                        boolean added = createdTask.addTool(toolList.get(i));
                    }
                }

                //once all added, we call for update
                ((MainActivity)getActivity()).requestTaskUpdate(createdTask);
                dialog.cancel(); //TODO remove when functionality added
            }
        });

        //cancel button function
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

    private double validateTime(String time) {

        double output;

        try {
            output = Double.parseDouble(time);
        }catch (Exception ex) {
            output = -1;
        }

        if (output <= 0) {
            output = -1;
        }
        return output;

    }

    private boolean validateDate(int year, int month, int day) {
        boolean valid = true;

        //Test February with 28 days
        if (month == 2) {
            boolean leapYear = ((year % 4) == 0);
            if ((day > 28 && !leapYear) || (day > 29 && leapYear)) {
                valid = false;
            }
        }

        //test 30 day month
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                valid = false;
            }
        }

        //Need future date
        Calendar presentCal = Calendar.getInstance();
        long presentDate = presentCal.getTimeInMillis();
        Calendar inputDateCal = Calendar.getInstance();
        inputDateCal.set(year, month-1, day);
        long wantedDate = inputDateCal.getTimeInMillis();
        if(wantedDate < presentDate) {
            valid = false;
        }

        return valid;

    }

    private int validateReward(String pts) {
        int output;

        try {
            output = Integer.parseInt(pts);
        }catch (Exception ex) {
            output = -1;
        }

        if (output <= 0) { output = -1; }
        return output;
    }

    private boolean checkAllFilled(String taskName, String taskTime, String taskReward, String taskNote) {
        boolean valid = true;

        if (taskName.isEmpty() || taskTime.isEmpty() || taskReward.isEmpty() || taskNote.isEmpty()) {
            valid = false;
        } return valid;
    }
}
