package ar.com.netmefy.netmefy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.netmefy.netmefy.R;
import ar.com.netmefy.netmefy.adapters.elements.OtItem;

/**
 * Created by ignac on 21/9/2017.
 */

public class MySimpleOrdenesArrayAdapter extends ArrayAdapter<OtItem> {
    private final Context context;
    private final OtItem[] values;

    public MySimpleOrdenesArrayAdapter(Context context, OtItem[] values) {
        super(context, R.layout.ot_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.ot_layout, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);
        TextView textView3 = (TextView) rowView.findViewById(R.id.thirdLine);
        TextView textView4 = (TextView) rowView.findViewById(R.id.fourthLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.circleImageView2);
        textView1.setText(values[position].getCliente_desc());
        textView2.setText(values[position].getCliente_direccion());
        textView3.setText(values[position].getCliente_tipo_casa());
        Integer statusOt = values[position].getEstado();

        if (values[position].getCliente_tipo_casa().equalsIgnoreCase("casa")) {
            imageView.setImageResource(R.drawable.casa_128);
        }else {
            imageView.setImageResource(R.drawable.dpto_128);

        }

        if (statusOt == 1) { //ESTADO ABIERTO
            rowView.setBackgroundColor(Color.LTGRAY);
            textView1.setTextColor(Color.BLACK);
            textView2.setTextColor(Color.BLACK);
            textView3.setTextColor(Color.BLACK);
            textView4.setTextColor(Color.BLACK);
            textView4.setText("PENDIENTE");
            //rowView.setBackgroundColor(Color.BLUE);
        } else if (statusOt == 2) { //ESTADO EN CURSO
            rowView.setBackgroundColor(Color.parseColor("#ff8c1a"));
            textView1.setTextColor(Color.WHITE);
            textView2.setTextColor(Color.WHITE);
            textView3.setTextColor(Color.WHITE);
            textView4.setTextColor(Color.WHITE);
            textView4.setText("EN CURSO");
            //rowView.setBackgroundColor(Color.YELLOW);
        } else if (statusOt==3){//ESTADO CERRADO
            rowView.setBackgroundColor(Color.parseColor("#00b300"));
            textView1.setTextColor(Color.WHITE);
            textView2.setTextColor(Color.WHITE);
            textView3.setTextColor(Color.WHITE);
            textView4.setTextColor(Color.WHITE);
            textView4.setText("CERRADA");
            //rowView.setBackgroundColor(Color.GREEN);
        }

        return rowView;
    }
}
