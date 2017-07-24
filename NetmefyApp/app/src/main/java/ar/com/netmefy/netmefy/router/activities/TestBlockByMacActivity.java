package ar.com.netmefy.netmefy.router.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.com.netmefy.netmefy.R;
import ar.com.netmefy.netmefy.router.Router;

public class TestBlockByMacActivity extends AppCompatActivity {
    EditText etMac;
    Router router;
    Button btnAdd, btnRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_block_by_mac);

        etMac = (EditText) findViewById(R.id.etMac);
        etMac.setText("aa:bb:cc:00:11:22");

        btnAdd = (Button)findViewById(R.id.btnAddBlock);
        btnRemove = (Button)findViewById(R.id.btnRemoveBlock);

        router = Router.getInstance(getApplicationContext());
    }

    public void addBlock(View v){
        Toast.makeText(getApplicationContext(), "addBlock", Toast.LENGTH_LONG).show();
        String mac ;

        mac = etMac.getText().toString();

        btnAdd.setText("addBlock ..." + mac);

        router.addBlockByMac(mac,
                new Response.Listener() {
                    @Override
                    public void onResponse(final Object response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //etSsid.setText("SSID OK");
                                //etPassword.setText("PASSWORD OK");
                                btnAdd.setText(response.toString());
                            }
                        });
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //etSsid.setText("SSID NO OK");
                                //etPassword.setText("PASSWORD NO OK");
                                btnAdd.setText("Error-"+error.getMessage());
                            }
                        });


                    }
                });



    }

    public void removeBlock(View v){
        Toast.makeText(getApplicationContext(), "removeBlock", Toast.LENGTH_LONG).show();
        String mac = etMac.getText().toString();

        btnRemove.setText("removeBlock..." + mac);

        router.removeBlockByMac(mac,
                new Response.Listener() {
                    @Override
                    public void onResponse(final Object response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //etSsid.setText("SSID OK");
                                //etPassword.setText("PASSWORD OK");
                                btnRemove.setText(response.toString());
                            }
                        });
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //etSsid.setText("SSID NO OK");
                                //etPassword.setText("PASSWORD NO OK");
                                btnRemove.setText("Error-"+error.getMessage());
                            }
                        });


                    }
                });

    }

}
