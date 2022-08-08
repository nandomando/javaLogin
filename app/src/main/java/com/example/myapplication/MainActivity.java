package com.example.myapplication;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.example.myapplication.Utils.LoadingDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private BottomSheetFragment bottomSheetFragment;
    private FloatingActionButton floatingActionButton;

    private RelativeLayout relativeLayout;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //replaceFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        floatingActionButton = findViewById(R.id.add_exercise_btn);
       // relativeLayout =  findViewById(R.id.bottom_sheet_fragment);
        constraintLayout = findViewById(R.id.bottomSheet);

       bottomSheetFragment = new BottomSheetFragment();
//        BottomSheetBehavior<RelativeLayout> bottomSheetBehavior = BottomSheetBehavior.from(relativeLayout);
//        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);



        floatingActionButton.setOnClickListener( view -> {

            showBottomSheetDialog();
        });

         bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 Log.d("", "onNavigationItemSelected:"+ item.getItemId());

                 switch (item.getItemId()){
                     case R.id.home:
                         replaceFragment(new HomeFragment());
                         break;
                     case R.id.book:
                         replaceFragment(new BookFragment());
                         break;
                     case R.id.stats:
                         replaceFragment(new StatsFragment());
                         break;
                     case R.id.profile:
                         replaceFragment(new ProfileFragment());
                         break;
                 }
                 return true;
             }
         });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomSheetDialog(){
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}




// binding = ActivityMainBinding.inflate(getLayoutInflater());
//setContentView(binding.getRoot());

//ActivityMainBinding binding;
