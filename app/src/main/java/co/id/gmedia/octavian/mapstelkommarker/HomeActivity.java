package co.id.gmedia.octavian.mapstelkommarker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.id.gmedia.coremodul.ApiVolley;
import co.id.gmedia.octavian.mapstelkommarker.adapter.TemplateAdaptorData;
import co.id.gmedia.octavian.mapstelkommarker.model.ModelData;
import co.id.gmedia.octavian.mapstelkommarker.server.AppSharedPreferences;
import co.id.gmedia.octavian.mapstelkommarker.server.Server;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MarkerOptions markerOptions = new MarkerOptions();
    private String tittle, nama, kota ;
    private CameraPosition cameraPosition;
    private LatLng latLng, center;
    private Button cari;
    private CardView btn_refresh, btn_radius, btn_akun, btn_filter;
    private String smg="SMG";
    private String dmk="DMK";
    private String knd="KND";
    private List<ModelData> listData = new ArrayList<>();
    private TemplateAdaptorData adaterData;
    private EditText txt_search;
    private String search = "";
    private ProgressBar loading;
    private static String TAG = "";
    private TextView userNM, emailNM;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        activity = this;

        //View
        txt_search = findViewById(R.id.txt_search);
        loading = findViewById(R.id.loading);
        btn_refresh = findViewById(R.id.refresh);
        btn_radius = findViewById(R.id.radius);
        btn_akun = findViewById(R.id.akun);
        btn_filter = findViewById(R.id.filter);

        RecyclerView rvData = findViewById(R.id.rv_data1);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false));
        adaterData = new TemplateAdaptorData(HomeActivity.this, listData);
        rvData.setAdapter(adaterData);

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ref = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(ref);
                finish();
            }
        });

        btn_radius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Cooming Soon", Toast.LENGTH_LONG).show();
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.popup_activity_filter);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final RadioGroup radio;
                final RadioButton btn_smg, btn_dmk, btn_knd;
                Button btn_proses;
                btn_proses = dialog.findViewById(R.id.proses);
                radio = dialog.findViewById(R.id.radio_grup);
                btn_smg = dialog.findViewById(R.id.btn_smg);
                btn_dmk = dialog.findViewById(R.id.btn_dmk);
                btn_knd = dialog.findViewById(R.id.btn_knd);

                btn_proses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int kode = radio.getCheckedRadioButtonId();
                        if (kode == btn_smg.getId()){
                            search = smg;
                           /* Intent intent = new Intent(dialog.getContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();*/

                        }else if (kode == btn_dmk.getId()){
                            search = dmk;
                            /*Intent intent = new Intent(dialog.getContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();*/

                        } else if (kode == btn_knd.getId()){
                            search = knd;
                            /*Intent intent = new Intent(dialog.getContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();*/
                        }

                        Log.d( "tes", search);
                        LoadData();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btn_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.activity_popup_akun2);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CardView btn_logout;
                btn_logout = dialog.findViewById(R.id.btn_logout);
                userNM = dialog.findViewById(R.id.usernaama);
                emailNM = dialog.findViewById(R.id.email);
                userNM.setText(AppSharedPreferences.getNama(HomeActivity.this));
                emailNM.setText(AppSharedPreferences.getEmail(HomeActivity.this));


                btn_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppSharedPreferences.Logout(HomeActivity.this);
                        Intent in = new Intent(HomeActivity.this, LoginActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                        finish();
                    }
                });

                dialog.show();
                //Toast.makeText(HomeActivity.this, "Cooming Soon", Toast.LENGTH_LONG).show();
            }
        });

        /*cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ActivityDataDaerah.class);
                startActivity(intent);
            }
        });*/

        txt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    search = textView.getText().toString();
                    LoadData();
                    return true;
                }
                return false;
            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0){
                    search = editable.toString();
                    LoadData();
                }
                Log.d("search",search);
            }
        });

        getMarker();
        LoadData();

    }

    private void LoadData() {
        loading.setVisibility(View.VISIBLE);
        String parameter = String.format(Locale.getDefault(),"?cari=%s", search);
        new ApiVolley(HomeActivity.this, new JSONObject(), "GET", Server.URL_DATA_SEARCH+parameter, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                loading.setVisibility(View.GONE);
                try {
                    listData.clear();
                    JSONArray array = new JSONArray(result);
                    for (int i=0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        listData.add(new ModelData(
                                object.getString("SITEID")
                                ,object.getString("SITENAME")
                                ,object.getString("LONGITUDE")
                                ,object.getString("LATITUDE")
                                ,object.getString("RADIUS")
                                ,object.getString("KABUPATEN")
                        ));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adaterData.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {
                loading.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Kesalahan Jaringan", Toast.LENGTH_LONG).show();
            }
        });
    }


    //medeklarasikan camera position MAPS
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //setlokasi Zoom
        latLng = new LatLng(-6.9902851,110.4207485);
        //mMap.setMinZoomPreference(15.0f);
        /*mMap.addMarker(new MarkerOptions().position(latLng).title("title"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/
        cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        getMarker();
    }

    private void addMarker(LatLng latlng, final String title, final String nama, final String kota ) {
        markerOptions.position(latlng);
        markerOptions.title(title);
        markerOptions.snippet(nama+", "+kota);

        mMap.addMarker(markerOptions);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMarker() {
        String parameter = String.format(Locale.getDefault(),"?cari=%s", search);
        new ApiVolley(HomeActivity.this, new JSONObject(), "GET", Server.URL_DATA_SEARCH+parameter, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray array = new JSONArray(result);
                    for (int i=0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        tittle = object.getString(Server.ID);
                        nama = object.getString(Server.NAMA);
                        kota = object.getString(Server.KAB);
                        latLng = new LatLng(Double.parseDouble(object.getString(Server.LATT)), Double.parseDouble(object.getString(Server.LONGG)));
                        addMarker(latLng, tittle, nama, kota);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(HomeActivity.this, "Kesalahan Jaringan", Toast.LENGTH_LONG).show();
            }
        });
    }


}
