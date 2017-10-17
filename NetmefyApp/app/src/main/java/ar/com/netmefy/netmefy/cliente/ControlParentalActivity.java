package ar.com.netmefy.netmefy.cliente;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import ar.com.netmefy.netmefy.ControlParentalWebPageSetUp;
import ar.com.netmefy.netmefy.DeviceListActivity;
import ar.com.netmefy.netmefy.R;
import ar.com.netmefy.netmefy.adapters.MySimpleWebPageArrayAdapter;
import ar.com.netmefy.netmefy.adapters.elements.WebPageToBlockItem;
import ar.com.netmefy.netmefy.router.Device;
import ar.com.netmefy.netmefy.router.Router;
import ar.com.netmefy.netmefy.services.NMF;
import ar.com.netmefy.netmefy.services.Utils;
import ar.com.netmefy.netmefy.services.api.Api;
import ar.com.netmefy.netmefy.services.api.entity.paginaControlParentalModel;
import ar.com.netmefy.netmefy.services.api.entity.webModel;

public class ControlParentalActivity extends AppCompatActivity {

    ListView webPageListView;
    Switch switchParentalControl;
    WebPageToBlockItem[] values;
    MySimpleWebPageArrayAdapter adapter;
    Router router ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_parental);
        webPageListView = (ListView) findViewById(R.id.lv_web_pages_list);

        router = Router.getInstance(getApplicationContext());

        final List<paginaControlParentalModel> paginas;// = new ArrayList<paginaControlParentalModel>() ;
        final Activity _this = this;
        final Api api = Api.getInstance(getApplicationContext());
        api.getListBlockedPage(new Response.Listener<List<paginaControlParentalModel>>() {
            @Override
            public void onResponse(List<paginaControlParentalModel> paginas) {

                //setteo las paginasbloqueadas
                for (paginaControlParentalModel p : paginas) {
                    for (webModel webBloqueada: NMF.cliente.router.webs_bloqueadas ) {
                        if(webBloqueada.id == p.id){
                            p.bloqueado = true;
                            break;
                        }
                        else
                            p.bloqueado = false;//esto no hace falta porque siempre viene en false
                    }
                }

                //cargo la lista que necesita la lista del activity
                values = new WebPageToBlockItem[paginas.size()];
                int i = 0;
                for (paginaControlParentalModel p : paginas) {
                    values[i] = p.toWebPageToBlockItem();
                    i++;
                }
                Handler onCompleteHandler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        //creo el evento del switch de arriba
                        switchParentalControl = (Switch) findViewById(R.id.switch_parent_control) ;
                        switchParentalControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                updateList(isChecked);
                                ///////////////////////////////////////////////////////////////////
                                final List<paginaControlParentalModel> paginasModel= new ArrayList<paginaControlParentalModel>();
                                for (int i = 0; i < webPageListView.getCount()-1; i++) {
                                    WebPageToBlockItem v = (WebPageToBlockItem) webPageListView.getItemAtPosition(i);
                                    paginaControlParentalModel p = v.toPaginaControlParentalModel();

                                    if(!isChecked)//si deschequea marco tod0 como false
                                        p.bloqueado = false ;

                                    paginasModel.add(p);
                                }

                                api.BlockPages(NMF.cliente.id, NMF.cliente.router.router_sk, paginasModel, new Response.Listener() {
                                    @Override
                                    public void onResponse(Object response) {

                                    }
                                });
                                ///////////////////////////////////////////////////////////////////
                            }
                        });//fin setOnCheckedCHange();
                        if(NMF.cliente.router.webs_bloqueadas.size()>0){

                            switchParentalControl.setChecked(true);
                            //updateList(true);
                        }

                        return false;
                    }
                });
                adapter = new MySimpleWebPageArrayAdapter(_this, values, onCompleteHandler);
                webPageListView.setAdapter(adapter);

                onCompleteHandler.sendEmptyMessage(0);

                ///////////////////////////////////////////////////////////////////
            }
        });
    }

    public void updateList(boolean isChecked){
        List<WebPageToBlockItem> pagesToAdd = new ArrayList<>();
        List<WebPageToBlockItem> pagesToRemove = new ArrayList<>();

        if(isChecked){
            for (int i = 0; i < adapter.getCount(); i++) {
                WebPageToBlockItem view =  adapter.getItem(i);
                if(view.getBlocked()){
                    //Utils.newToast(getApplicationContext(), "aa");
                    view.setReadOnly(true);
                    pagesToAdd.add(view);
                }else{
                    //Utils.newToast(getApplicationContext(), "bb");
                    view.setReadOnly(true);
                }
            }
            webPageListView.invalidateViews();

        }else{
            for (int i = 0; i < adapter.getCount(); i++) {
                WebPageToBlockItem view =  adapter.getItem(i);
                pagesToRemove.add(view);
                view.setReadOnly(false);
            }

            webPageListView.invalidateViews();
        }
        String url = "";
        //////////////////////////////////////////////
        for (WebPageToBlockItem item : pagesToAdd) {
            url = item.getUrl();
            router.addBlockByUrl(url, new Response.Listener() {
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

                }
            });
        }
        ////////////////////////////////////////////////////////////////////
        //TODO: FALTA AGREGAR QUE SOLO BORRE LAS PAGINAS QUE EXISTAN, LAS QUE NO EXISTAN QUE NO LAS BORRE
        for (WebPageToBlockItem item : pagesToRemove) {
            url = item.getUrl();
            router.removeBlockByUrl(url, new Response.Listener() {
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

                }
            });
        }
        //////////////////////////////////////////////////
    }

    public void newPage(View view){
        Intent page = new Intent(ControlParentalActivity.this, ControlParentalWebPageSetUp.class).putExtra("url", "");
        //startActivity(device);
        //Intent intent = new Intent(this, SyncActivity.class);
        //intent.putExtra("someData", "Here is some data");
        startActivityForResult(page, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent refresh = new Intent(this, ControlParentalActivity.class);
        startActivity(refresh);
        this.finish();
        /*if(resultCode==RESULT_OK){
            Intent refresh = new Intent(this, ControlParentalActivity.class);
            startActivity(refresh);
            this.finish();
        }*/
    }

}
