package com.muibsols.allinoneshopping.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muibsols.allinoneshopping.Adapters.StoreAdapter;
import com.muibsols.allinoneshopping.ClickListeners.StoreClickListener;
import com.muibsols.allinoneshopping.Models.Store;
import com.muibsols.allinoneshopping.R;
import com.muibsols.allinoneshopping.UI.BrowserActivity;
import com.muibsols.allinoneshopping.UI.MainViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Fragment implements StoreClickListener {


    StoreAdapter storeAdapter;
    RecyclerView storesRV;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Store> storeList;
    ProgressBar progressBar;
    MainViewModel mainViewModel;
    public Movie() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storeList = new ArrayList<>();
        mainViewModel =  ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stores, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getStores("Movie");
    }

    void initViews(View view) {
        storesRV = view.findViewById(R.id.storesRV);
        storeAdapter = new StoreAdapter(getContext(), this);
        storesRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        storesRV.setLayoutManager(new LinearLayoutManager(this));
        storesRV.setAdapter(storeAdapter);
//        storeAdapter.setList(storeHelper.getStoreModels());
        progressBar = view.findViewById(R.id.progressBar2);

        storesRV.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        search();
    }

    void search() {
        mainViewModel.getMutableLiveData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("HELL", "onChanged: "+s);
                if (TextUtils.isEmpty(s)) {
                    storeAdapter.getFilter().filter("");
                } else {
                    storeAdapter.getFilter().filter(s);
                }
            }
        });
    }

    void getStores(String category) {
        databaseReference.child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Store store = dataSnapshot.getValue(Store.class);
                    storeList.add(store);
                }
                storeAdapter.setList(storeList);
                progressBar.setVisibility(View.INVISIBLE);
                storesRV.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void storeOnClick(Store store) {
        Intent intent = new Intent(getActivity(), BrowserActivity.class);
        intent.putExtra("url", store.getStoreURL());
        startActivity(intent);
    }
}