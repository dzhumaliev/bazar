package solutions.isky.gaurangarevolution.presentation.ui.info_ad;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;

public class AdMapPosition extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private GoogleMap mMap;
    private double lat;
    private double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_map_position);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        lat=getIntent().getDoubleExtra("lat",0);
        lng=getIntent().getDoubleExtra("lng",0);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng item = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(item));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(item,14));
    }
}