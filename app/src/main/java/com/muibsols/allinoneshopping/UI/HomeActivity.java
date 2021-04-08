package com.muibsols.allinoneshopping.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.muibsols.allinoneshopping.Adapters.CategoryAdapter;
import com.muibsols.allinoneshopping.Adapters.StoreAdapter;
import com.muibsols.allinoneshopping.ClickListeners.CategoryClickListener;
import com.muibsols.allinoneshopping.ClickListeners.StoreClickListener;
import com.muibsols.allinoneshopping.Helpers.CategoryHelper;
import com.muibsols.allinoneshopping.Models.CategoryModel;
import com.muibsols.allinoneshopping.Models.Store;
import com.muibsols.allinoneshopping.R;
import com.muibsols.allinoneshopping.UI.Fragments.BabyKids;
import com.muibsols.allinoneshopping.UI.Fragments.Fashion;
import com.muibsols.allinoneshopping.UI.Fragments.Food;
import com.muibsols.allinoneshopping.UI.Fragments.Furniture;
import com.muibsols.allinoneshopping.UI.Fragments.Gifts;
import com.muibsols.allinoneshopping.UI.Fragments.Grocery;
import com.muibsols.allinoneshopping.UI.Fragments.HomeServices;
import com.muibsols.allinoneshopping.UI.Fragments.Medicine;
import com.muibsols.allinoneshopping.UI.Fragments.Movie;
import com.muibsols.allinoneshopping.UI.Fragments.Recharge;
import com.muibsols.allinoneshopping.UI.Fragments.TopStores;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements CategoryClickListener, StoreClickListener {

    public static final String TAG = "HOMEACTIVITY";

    private DrawerLayout drawer;
    NavigationView navigationView;
    ImageView drawerToggle;
    ImageView addStoreBT;
    RecyclerView categoryRV;
    CategoryAdapter categoryAdapter;
    CategoryHelper categoryHelper;
    EditText searchET;

    StoreAdapter storeAdapter;
    RecyclerView storesRV;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Store> storeList;

    ProgressBar progressBar;

    TabLayout tabLayout;
    ViewPager viewPager;

    MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mainViewModel =  ViewModelProviders.of(this).get(MainViewModel.class);

        storeList = new ArrayList<>();
//        getStores("Top Stores");
        search();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.home:
//                        startActivity(new Intent(MainActivity.this,ZodiacSignSettings.class));
                        break;
                    case R.id.submitStore:
                        startActivity(new Intent(HomeActivity.this, AddStoreActivity.class));
                        break;
                    case R.id.rateApp:
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                        } catch (android.content.ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                        }
                        break;
                    case R.id.suggestFeature:
                        EmailIntentBuilder.from(getApplicationContext())
                                .to("ikhhn313@gmail.com")
                                .subject("Feature Suggestion from All In One (Pakistan) App")
                                .body("Your Suggestion Here")
                                .start();
                        break;
                    case R.id.reportBuG:
                        EmailIntentBuilder.from(getApplicationContext())
                                .to("ikhhn313@gmail.com")
                                .subject("Bug Report from All In One (Pakistan) App")
                                .body("Your Bug Here")
                                .start();
                        break;
                    case R.id.shareApp:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Checkout This Awesome All In One (Pakistan) App at : http://play.google.com/store/apps/details?id=" + getApplication().getPackageName());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;
                    case R.id.privacyPolicy:
                        String url = "https://all-in-one-8.flycricket.io/privacy.html";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        drawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        addStoreBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddStoreActivity.class));
            }
        });

    }

    void initViews() {
        drawer = findViewById(R.id.drawer_layout);
        drawerToggle = findViewById(R.id.drawer_toggle);
        navigationView = findViewById(R.id.nav_view);
//        categoryRV = findViewById(R.id.categoryRV);
        addStoreBT = findViewById(R.id.addStoreBT);
        searchET = findViewById(R.id.searchET);
//        progressBar = findViewById(R.id.progressBar2);
/*        categoryAdapter = new CategoryAdapter(this, this);
        categoryRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRV.setAdapter(categoryAdapter);
        categoryHelper = new CategoryHelper();
        categoryAdapter.setList(categoryHelper.getCategoryModels());*/

/*//        storesRV = findViewById(R.id.storesRV);
        storeAdapter = new StoreAdapter(this, this);
        storesRV.setLayoutManager(new GridLayoutManager(this, 2));
//        storesRV.setLayoutManager(new LinearLayoutManager(this));
        storesRV.setAdapter(storeAdapter);
//        storeAdapter.setList(storeHelper.getStoreModels());*/
        //

        //viewpager and tabs
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopStores(), "Top Stores");
        adapter.addFragment(new Fashion(),"Fashion");
        adapter.addFragment(new Food(),"Food");
        adapter.addFragment(new Grocery(),"Grocery");
        adapter.addFragment(new Gifts(),"Gifts");
        adapter.addFragment(new HomeServices(),"Home Services");
        adapter.addFragment(new Medicine(),"Medicine");
        adapter.addFragment(new Movie(),"Movie");
        adapter.addFragment(new Furniture(),"Furniture");
        adapter.addFragment(new Recharge(),"Recharge");
        adapter.addFragment(new BabyKids(),"Baby Kids");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void categoryOnClick(CategoryModel category) {
//        getStores(category.getCategory());
        progressBar.setVisibility(View.VISIBLE);
        storesRV.setVisibility(View.INVISIBLE);
    }

    @Override
    public void storeOnClick(Store store) {
        Intent intent = new Intent(HomeActivity.this, BrowserActivity.class);
        intent.putExtra("url", store.getStoreURL());
        startActivity(intent);
    }

    void getStores(String category) {
        databaseReference.child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Store store = dataSnapshot.getValue(Store.class);
                    storeList.add(store);
                    Log.i(TAG, "onDataChange: " + store.getStoreName());
                }
                storeAdapter.setList(storeList);
                progressBar.setVisibility(View.INVISIBLE);
                storesRV.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
                storesRV.setVisibility(View.VISIBLE);
            }
        });
    }

    void search() {
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mainViewModel.setMutableLiveData(charSequence.toString());





            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}