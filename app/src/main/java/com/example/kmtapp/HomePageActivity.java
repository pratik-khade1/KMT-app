package com.example.kmtapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomePageActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView stop1, stop2, stop3, stop4, stop5;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize views
        SearchView searchBar = findViewById(R.id.search_bar);
        Button btnSeeAll = findViewById(R.id.btnSeeAll);
        stop1 = findViewById(R.id.stop_1);
        stop2 = findViewById(R.id.stop_2);
        stop3 = findViewById(R.id.stop_3);
        stop4 = findViewById(R.id.stop_4);
        stop5 = findViewById(R.id.stop_5);
        TextView scheduleInfo = findViewById(R.id.schedule_info);
        //CardView cardNearestStops = findViewById(R.id.card_nearest_stops);
        //CardView cardSchedules = findViewById(R.id.card_schedules);

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Set search functionality
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBusStops(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // See All button functionality
        btnSeeAll.setOnClickListener(v -> {
            Toast.makeText(HomePageActivity.this, "Showing all nearest bus stops", Toast.LENGTH_SHORT).show();
            // Implement logic to display all bus stops
        });

        // Set sample data for bus stops
        setSampleBusStops();

        // Set schedule information (sample data)
        scheduleInfo.setText(R.string.no_schedules_available_for_the_selected_bus_stops);
    }

    // Set sample bus stops for demonstration
    @SuppressLint("SetTextI18n")
    private void setSampleBusStops() {
        stop1.setText("Bus Stop 1: Main Square");
        stop2.setText("Bus Stop 2: Park Avenue");
        stop3.setText("Bus Stop 3: Central Station");
        stop4.setText("Bus Stop 4: High Street");
        stop5.setText("Bus Stop 5: City Mall");
    }

    // Perform bus stop search logic here
    private void searchBusStops(String query) {
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
        // Implement search functionality to find bus stops based on user input
    }

    // When the map is ready
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Example: Add a marker at a specific location and move the camera
        LatLng exampleLocation = new LatLng(18.5204, 73.8567); // Pune, India (example)
        mMap.addMarker(new MarkerOptions().position(exampleLocation).title("Bus Stop: Main Square"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(exampleLocation, 12));

        // You can add more markers for other bus stops if needed
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.clear();
            // Add markers or other map-related features on resume if needed
        }
    }
}
