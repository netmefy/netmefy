package ar.com.netmefy.netmefy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.com.netmefy.netmefy.adapters.elements.DeviceItem;
import ar.com.netmefy.netmefy.router.Router;
import ar.com.netmefy.netmefy.services.NMF_Info;
import ar.com.netmefy.netmefy.services.Utils;
import ar.com.netmefy.netmefy.services.api.Api;
import ar.com.netmefy.netmefy.services.api.entity.DeviceModel;
import ar.com.netmefy.netmefy.services.api.entity.dispositivoInfo;

public class DeviceSetUpActivity extends AppCompatActivity {

    de.hdodenhof.circleimageview.CircleImageView circleImageView2;
    EditText et_apodo;
    TextView et_tipo;
    TextView tv_mac;
    Button btn_bloquear;
    Router router;
    dispositivoInfo device_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_set_up);

        router = Router.getInstance(getApplicationContext());

        String mac= getIntent().getStringExtra("mac");

        //DeviceItem[] values =router.getDeviceConnectedStored();
        /*DeviceItem[] values = new DeviceItem[] { new DeviceItem("11:22:33:44:55:66","Pepe",R.drawable.guest_w_128, "TV", Boolean.FALSE),
                new DeviceItem("11:22:33:44:55:66","Android",R.drawable.download_128, "TV",  Boolean.FALSE),
                new DeviceItem("11:22:33:44:55:66","iPhone",R.drawable.info_128, "TV",  Boolean.TRUE),
                new DeviceItem("11:22:33:44:55:66","WindowsMobile",R.drawable.add_link_128, "TV",  Boolean.FALSE),
                new DeviceItem("11:22:33:44:55:66","Blackberry",R.drawable.eye_128, "TV",  Boolean.TRUE),
                new DeviceItem("11:22:33:44:55:66","WebOS",R.drawable.help_128, "TV",  Boolean.TRUE),
                new DeviceItem("11:22:33:44:55:66","Ubuntu",R.drawable.ok_128, "TV",  Boolean.FALSE),
                new DeviceItem("11:22:33:44:55:66","Windows7",R.drawable.lock_5_128, "TV",  Boolean.TRUE),
                new DeviceItem("11:22:33:44:55:66","OS/2",R.drawable.save_128, "TV",  Boolean.FALSE),
                new DeviceItem("11:22:33:44:55:66","Max OS X",R.drawable.search_128, "TV",  Boolean.TRUE)};
        */

        device_selected = NMF_Info.findDeviceByMac(mac);


        circleImageView2 = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.tvDeviceConnected2);
        et_tipo = (TextView) findViewById(R.id.et_device_config_tipo);
        tv_mac = (TextView) findViewById(R.id.tv_device_config_mac);
        et_apodo = (EditText) findViewById(R.id.et_device_config_apodo);
        btn_bloquear = (Button) findViewById(R.id.button2);

        circleImageView2.setImageResource(device_selected.resId);
        tv_mac.setText(device_selected.mac);
        et_tipo.setText(device_selected.tipo);
        et_apodo.setText(device_selected.apodo);

        _set_blocked(device_selected.bloqueado);
    }

    private void _set_blocked(boolean blocked){
        if(blocked){
            btn_bloquear.setText("Desbloquear");
            btn_bloquear.setBackgroundColor(Color.parseColor("#ff99cc00"));
        }else{
            btn_bloquear.setText("Bloquear");
            btn_bloquear.setBackgroundColor(Color.parseColor("#ffff4444"));
        }

    }

    private boolean esta_bloqueado(){
        return btn_bloquear.getText().toString().toLowerCase().startsWith("bloq");
    }

    public void block(View view){
        _set_blocked(esta_bloqueado());
    }

    public void saveAndExit(View view){

        final ProgressDialog progressBar = Utils.getProgressBar(this, "Actualizando ...");
        progressBar.show();


        String apodo;
        String tipo;
        String mac;
        boolean bloqueado;
        apodo = et_apodo.getText().toString();
        tipo = et_tipo.getText().toString();
        bloqueado = !esta_bloqueado();
        mac = tv_mac.getText().toString();

        boolean cambio ;
        final Activity _this = this;


        /////////////////////////////////////////////////
        final dispositivoInfo di = NMF_Info.findDeviceByMac(mac);

        di.apodo = apodo;
        di.tipo  = tipo;
        di.bloqueado = bloqueado;
        final Api api = Api.getInstance(getApplicationContext());
        DeviceModel dm = di.toDeviceModel();
        dm.cliente_sk = NMF_Info.clientInfo.id;
        dm.router_sk = NMF_Info.clientInfo.router.router_sk;

        api.updateDevice(dm, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                if(!response.toString().equals("error")){
                    NMF_Info.setDevice(di);
                    //_this.finish();
                    progressBar.hide();
                    progressBar.dismiss();
                    _this.setResult(RESULT_OK, null);

                    _this.finish();
                }else{
                    progressBar.hide();
                    progressBar.dismiss();

                }


            }
        });

        /*
        if(device_selected.bloqueado!=bloqueado){
            final dispositivoInfo di = NMF_Info.findDeviceByMac(mac);

            di.apodo = apodo;
            di.tipo  = tipo;
            di.bloqueado = bloqueado;
            final Api api = Api.getInstance(getApplicationContext());



            if(bloqueado){
                router.addBlockByMac(di.mac, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        DeviceModel dm = di.toDeviceModel();
                        dm.cliente_sk = NMF_Info.clientInfo.id;
                        dm.router_sk = NMF_Info.clientInfo.router.router_sk;
                        api.updateDevice(dm, new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {

                                NMF_Info.setDevice(di);
                                //_this.finish();
                                _this.setResult(RESULT_OK, null);
                                _this.finish();

                            }
                        });
                    }
                });
            }else{
                router.removeBlockByMac(di.mac, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        DeviceModel dm = di.toDeviceModel();
                        dm.cliente_sk = NMF_Info.clientInfo.id;
                        dm.router_sk = NMF_Info.clientInfo.router.router_sk;
                        api.updateDevice(dm, new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {

                                NMF_Info.setDevice(di);
                                //_this.finish();
                                _this.setResult(RESULT_OK, null);
                                _this.finish();

                            }
                        });
                    }
                });

            }


        }*/

        /////////////////////////////////////////////////




    }

}