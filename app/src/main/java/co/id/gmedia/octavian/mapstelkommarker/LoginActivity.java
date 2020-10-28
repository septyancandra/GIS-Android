package co.id.gmedia.octavian.mapstelkommarker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import co.id.gmedia.coremodul.ApiVolley;
import co.id.gmedia.octavian.mapstelkommarker.server.AppSharedPreferences;
import co.id.gmedia.octavian.mapstelkommarker.server.Server;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_username, txt_pass;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(AppSharedPreferences.isLoggedIn(this)){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        //View

        txt_username = findViewById(R.id.txt_username);
        txt_pass = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLogin();
            }
        });


    }

    private void getLogin() {
        JSONObject object = new JSONObject();
        try {
            object.put("user", txt_username.getText().toString());
            object.put("password", txt_pass.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new ApiVolley(LoginActivity.this, object, "POST", Server.URL_LOGIN, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject ob = new JSONObject(result);
                    String status = ob.getString("success");
                    String message = ob.getString("message");

                    if (status.equals("1")){
                        JSONObject obj = ob.getJSONObject("details");

                        AppSharedPreferences.Login(LoginActivity.this,
                        obj.getString("id"),
                        obj.getString("firtsname"),
                        obj.getString("email"));

                        obj.getString("lastname");
                        obj.getString("username");
                        obj.getString("password");
                        obj.getString("datetime");

                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    } else{
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(LoginActivity.this, "Kesalahan Jaringan", Toast.LENGTH_LONG).show();
            }
        });
    }
}
