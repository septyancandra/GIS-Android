package co.id.gmedia.octavian.mapstelkommarker;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import co.id.gmedia.octavian.mapstelkommarker.model.ModelData;
import co.id.gmedia.octavian.mapstelkommarker.server.Server;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ModelData nota;
    private double lat;
    private double lon;
    private CameraPosition cameraPosition;
    private TextView latid, longitid, id_daerah, nama, tempat, kota;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(getIntent().hasExtra(Server.EXTRA_DAERAH)){
            Gson gson = new Gson();
            nota = gson.fromJson(getIntent().getStringExtra(Server.EXTRA_DAERAH), ModelData.class);
        }

        latid = findViewById(R.id.lotitude);
        latid.setText(String.valueOf(nota.getItem3()));
        longitid = findViewById(R.id.longitude);
        longitid.setText(String.valueOf(nota.getItem4()));
        id_daerah = findViewById(R.id.id_daerah);
        id_daerah.setText(String.valueOf(nota.getItem1()));
        tempat = findViewById(R.id.nama);
        tempat.setText(String.valueOf(nota.getItem2()));
        kota = findViewById(R.id.alamat);
        kota.setText(String.valueOf(nota.getItem6()));
        img_back = findViewById(R.id.btn_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MapsActivity.this, ActivityDataDaerah.class);
                startActivity(intent);
                finish();*/
                onBackPressed();
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        lat = Double.parseDouble(nota.getItem4());
        lon = Double.parseDouble(nota.getItem3());
        String title = nota.getItem1();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);
        /*CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(40).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
        //mMap.setMinZoomPreference(15.0f);
        mMap.addMarker(new MarkerOptions().position(sydney).title(title));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}
