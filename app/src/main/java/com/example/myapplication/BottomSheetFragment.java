package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Models.Exercise;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    EditText exerciseName;
    EditText exerciseWeight;
    Button decreaseSets;
    Button increaseSets;
    TextView numberOfSets;
    Button decreaseReps;
    Button increaseReps;
    TextView numberOfReps;
    Button saveExerciseBtn;
    int counterSets;
    int counterReps;


    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        exerciseName = view.findViewById(R.id.name_exercise_input);
        exerciseWeight = view.findViewById(R.id.weight_exercise_input);
        decreaseSets = view.findViewById(R.id.decrease_sets_btn);
        increaseSets = view.findViewById(R.id.increase_sets_btn);
        numberOfSets = view.findViewById(R.id.numberOfSets);
        decreaseReps = view.findViewById(R.id.decrease_reps_btn);
        increaseReps= view.findViewById(R.id.increase_reps_btn);
        numberOfReps = view.findViewById(R.id.numberOfReps);
        saveExerciseBtn = view.findViewById(R.id.save_exercise_btn);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return view;

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        counterSets = 0;
        counterReps = 0;

        decreaseSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(counterSets > 0) {
                    counterSets = decreaseNumber(counterSets);
                    numberOfSets.setText(String.valueOf(counterSets));
                }
            }
        });

        increaseSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterSets >= 0){
                    counterSets = increaseNumber(counterSets);
                    numberOfSets.setText(String.valueOf(counterSets));
                }
            }
        });

        decreaseReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterReps > 0){
                    counterReps = decreaseNumber(counterReps);
                    numberOfReps.setText(String.valueOf(counterReps));
                }
            }
        });

        increaseReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterReps >= 0){
                    counterReps = increaseNumber(counterReps);
                    numberOfReps.setText(String.valueOf(counterReps));
                }
            }
        });

        saveExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(exerciseName.getText()) &&
                        !TextUtils.isEmpty(exerciseWeight.getText()) &&
                        numberOfSets.getText() != Integer.toString(0) &&
                        numberOfReps.getText() != Integer.toString(0)
                ){
                    saveWorkOut(exerciseName.getText().toString(),
                            exerciseWeight.getText().toString(),
                            numberOfSets.getText().toString(),
                            numberOfReps.getText().toString());
                }
            }
        });
    }


    @Override public int getTheme() {
        return R.style.CustomBottomSheetDialogTheme;
    }

    public int increaseNumber(int number){
        number +=1;
        return number;
    }

    public int decreaseNumber(int number){
        number -= 1;
        return number;
    }

    public void saveWorkOut(String name, String weight, String sets, String reps){
        if(!TextUtils.isEmpty(exerciseName.getText()) &&
                !TextUtils.isEmpty(exerciseWeight.getText()) &&
                numberOfSets.getText() != Integer.toString(0) &&
                numberOfReps.getText() != Integer.toString(0)
        ){
            ////create workout
            Exercise exercise = new Exercise(name, weight, sets, reps);
            Log.d("", "saveWorkOut:" + exercise.getName() + exercise.getWeight() + exercise.getReps()+ exercise.getSets());
        }

    }
}
