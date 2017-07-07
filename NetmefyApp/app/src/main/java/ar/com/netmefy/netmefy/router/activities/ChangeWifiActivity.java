package ar.com.netmefy.netmefy.router.activities;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.com.netmefy.netmefy.R;
import ar.com.netmefy.netmefy.router.ConfigWifi;
import ar.com.netmefy.netmefy.router.Router;
import ar.com.netmefy.netmefy.router.tplink.TLWR941ND.TPLink;

public class ChangeWifiActivity extends AppCompatActivity {

    Router router;

    EditText etSsid;
    EditText etPassword;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_wifi);

        etSsid = (EditText) findViewById(R.id.etSsid);
        etPassword= (EditText) findViewById(R.id.etPassword);
        btn = (Button) findViewById(R.id.btnChangeWifi);

        btn.setEnabled(false);

        router = new TPLink(this.getApplicationContext());

        router.getConfigWifi(new Response.Listener<ConfigWifi>() {
            @Override
            public void onResponse(ConfigWifi configWifi) {
                etSsid.setText(configWifi.getSsid());
                etPassword.setText(configWifi.getPassword());

                btn.setEnabled(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                etSsid.setText("ERR!!!");
                etPassword.setText("ERRR!!!");
            }
        });

        /*
        router.getWifiSsid(new Response.Listener<String>() {
                               @Override
                               public void onResponse(String ssid) {
                                   etSsid.setText(ssid);
                               }
                           }, new Response.ErrorListener() {
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   etSsid.setText("ERROR!!!");;
                               }
                           }
        );

        router.getWifiPassword(new Response.Listener<String>() {
                               @Override
                               public void onResponse(String password) {
                                   etPassword.setText(password);
                               }
                           }, new Response.ErrorListener() {
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   etPassword.setText("ERROR!!!");;
                               }
                           }
        );*/
    }


    public void change(View view){
        String newSsid = etSsid.getText().toString();
        String newPassword = etPassword.getText().toString();

        if(newPassword.length()<=8){
            Toast.makeText(getApplicationContext(), "La pass debe ser mayor o igual a ocho!!!!!", Toast.LENGTH_LONG).show();
            return;
        }
        if(newSsid.length() == 0){
            Toast.makeText(getApplicationContext(), "completar ssid!!!!!", Toast.LENGTH_LONG).show();
            return;
        }

        final ConfigWifi configWifi = new ConfigWifi();
        configWifi.setSsid(newSsid);
        configWifi.setPassword(newPassword);

        router.setConfigWifiAndRestart(configWifi,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etSsid.setText("SSID OK");
                                etPassword.setText("PASSWORD OK");
                            }
                        });

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etSsid.setText("SSID NO OK");
                                etPassword.setText("PASSWORD NO OK");
                            }
                        });

                    }
                });


    }


}