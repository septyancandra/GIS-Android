package co.id.gmedia.octavian.mapstelkommarker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.id.gmedia.coremodul.ApiVolley;
import co.id.gmedia.octavian.mapstelkommarker.adapter.TemplateAdaptorData;
import co.id.gmedia.octavian.mapstelkommarker.model.ModelData;
import co.id.gmedia.octavian.mapstelkommarker.server.Server;
/*import co.id.gmedia.octavian.telkommaps.adapter.TemplateAdaptorData;
import co.id.gmedia.octavian.telkommaps.model.ModelData;
import co.id.gmedia.octavian.telkommaps.server.Server;*/

public class ActivityDataDaerah extends AppCompatActivity {

    private List<ModelData> listData = new ArrayList<>();
    private TemplateAdaptorData adaterData;
    private EditText txt_search;
    private String search = "";
    private ProgressBar loading;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_daerah);

        txt_search = findViewById(R.id.txt_search);
        loading = findViewById(R.id.loading);
        btn_back = findViewById(R.id.btn_back);

        RecyclerView rvData = findViewById(R.id.rv_data);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setLayoutManager(new LinearLayoutManager(ActivityDataDaerah.this, LinearLayoutManager.VERTICAL, false));
        adaterData = new TemplateAdaptorData(ActivityDataDaerah.this, listData);
        rvData.setAdapter(adaterData);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(ActivityDataDaerah.this, HomeActivity.class);
                startActivity(intent);
                finish();*/
                onBackPressed();
            }
        });

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

        LoadData();
    }

    private void LoadData() {
        loading.setVisibility(View.VISIBLE);
        String parameter = String.format(Locale.getDefault(),"?cari=%s", search);
        new ApiVolley(ActivityDataDaerah.this, new JSONObject(), "GET", Server.URL_DATA_SEARCH+parameter, new ApiVolley.VolleyCallback() {
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
                Toast.makeText(ActivityDataDaerah.this, "Kesalahan Jaringan", Toast.LENGTH_LONG).show();
            }
        });
    }
}
