package solutions.isky.gaurangarevolution.presentation.ui.addad;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.LocationAddress;

public class SelectOnMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    protected GoogleApiClient mGoogleApiClient;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private LatLng center;
    private double lat = 0, lng = 0;
    private String locationAddress;
    @BindView(R.id.progressAdress)
    ProgressBar show_pr_adress;
    @BindView(R.id.textLoc)
    TextView adress_order;
    @BindView(R.id.btn_chek)
    Button btn_chek;
    private FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.select_address_on_map);
        ButterKnife.bind(this);
        btn_chek.setOnClickListener(view -> {
            if (lat != 0 && lng != 0) {
                Intent intent = new Intent();
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("locationAddress", locationAddress);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void setUpMapIfNeeded() {

        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onCameraIdle() {
        center = mMap.getCameraPosition().target;
        List<Address> addresses = null;

        try {
            show_pr_adress.setVisibility(View.VISIBLE);
            adress_order.setText("");
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(center.latitude, center.longitude,
                    getApplicationContext(), new GeocoderHandler());


        } catch (Exception e) {
            e.printStackTrace();
            show_pr_adress.setVisibility(View.GONE);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(this);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SelectOnMap.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        setPosition();
    }

    private void setPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            String provider = lm.getBestProvider(criteria, true);
            myLocation = lm.getLastKnownLocation(provider);
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if(location!=null){
                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            if (location != null) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12), 1500, null);
                            }
                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("MapsActivity", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            setPosition();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                //mMap.addMarker(new MarkerOptions().position(Bishkek).title("Marker in Bishkek"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    lat = bundle.getDouble("lat", 0);
                    lng = bundle.getDouble("lng", 0);
                    break;
                default:
                    locationAddress = "";
            }

            show_pr_adress.setVisibility(View.GONE);
            adress_order.setText(locationAddress);
        }
    }
}
