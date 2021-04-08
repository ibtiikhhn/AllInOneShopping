package com.muibsols.allinoneshopping.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.muibsols.allinoneshopping.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddStoreActivity extends AppCompatActivity {

    AppCompatSpinner firstSpinner;
    String selectedCategory = "";
    String[] array = {"Fashion", "Grocery", "Recharge", "Baby & Kids", "Food", "Movie", "Furniture", "Gifts", "Home Services"};

    ImageView backBT;
    EditText nameET;
    EditText storeUrlET;
    EditText descriptionET;
    Button submitBT;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        initViews();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameET.getText().toString().isEmpty()) {
                    Toast.makeText(AddStoreActivity.this, "Empty Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (storeUrlET.getText().toString().isEmpty()) {
                    Toast.makeText(AddStoreActivity.this, "Empty URL!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedCategory.isEmpty()) {
                    Toast.makeText(AddStoreActivity.this, "Choose Category!", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> map = new HashMap<>();

                map.put("StoreName", nameET.getText().toString());
                map.put("StoreCategory", selectedCategory);
                map.put("StoreURL", storeUrlET.getText().toString());
                if (descriptionET.getText().toString().isEmpty()) {
                    map.put("StoreDescription", "No Description Provided");
                } else {
                    map.put("StoreDescription", descriptionET.getText().toString());
                }

                databaseReference.child("Requests").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddStoreActivity.this, "Request Submitted Successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddStoreActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void initViews() {
        backBT = findViewById(R.id.storeBackET);
        nameET = findViewById(R.id.storeNameET);
        storeUrlET = findViewById(R.id.storeURLET);
        descriptionET = findViewById(R.id.storeDesET);
        submitBT = findViewById(R.id.submitBT);
        firstSpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, array);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_background_text);
        firstSpinner.setAdapter(spinnerArrayAdapter);
    }
}