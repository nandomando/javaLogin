package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Models.User;
import com.example.myapplication.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountActivity extends AppCompatActivity {

private EditText userName;
private EditText userEmail;
private EditText userPassword;
private EditText userPhone;
private Button createUserButton;
private FirebaseUser currentUser;

private FirebaseAuth firebaseAuth;
private FirebaseFirestore db = FirebaseFirestore.getInstance();
private CollectionReference collectionReference = db.collection("Users");
private FirebaseAuth.AuthStateListener authStateListener;

private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        userName = findViewById(R.id.editTextName);
        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);
        userPhone = findViewById(R.id.editTextMobile);
        createUserButton = findViewById(R.id.create_user_button);

        firebaseAuth = FirebaseAuth.getInstance();

        loadingDialog = new LoadingDialog(CreateAccountActivity.this);

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(userName.getText().toString())
                        && !TextUtils.isEmpty(userEmail.getText().toString())
                        && !TextUtils.isEmpty(userPassword.getText().toString())
                        && !TextUtils.isEmpty(userPhone.getText().toString())
                ){
                    String username = userName.getText().toString();
                    String useremail = userEmail.getText().toString();
                    String userpassword = userPassword.getText().toString();
                    String userphone = userPhone.getText().toString();

                    onCreateUser(username, useremail, userpassword, userphone);
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Empty fills not allow", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void onCreateUser(String name, String email, String password, String phone){
        if(!TextUtils.isEmpty(userName.getText().toString())
                && !TextUtils.isEmpty(userEmail.getText().toString())
                && !TextUtils.isEmpty(userPassword.getText().toString())
                && !TextUtils.isEmpty(userPhone.getText().toString())
        ){

            loadingDialog.startLoadingDialog();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        currentUser = firebaseAuth.getCurrentUser();
                        String currenUserId = currentUser.getUid();
                        User user = new User(currenUserId, name, email, password, phone);
                        collectionReference.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.getResult().exists()) {
                                            loadingDialog.dismissDialog();
                                            Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loadingDialog.dismissDialog();
                                        //something went wrong
                                    }
                                });
                            }
                        });

                    } else {}
                }
            });
        } else {}

    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }


}

//@Override
//protected void onStart() {
//        super.onStart();
//        currentUser = firebaseAuth.getCurrentUser();
//        firebaseAuth.addAuthStateListener(authStateListener);
//        }