package ar.com.netmefy.netmefy.services.api.entity;

import java.math.BigDecimal;

import ar.com.netmefy.netmefy.adapters.elements.GestionItem;

/**
 * Created by fiok on 07/10/2017.
 */

public class reclamoListItemModel {
    public int ot_id;
    public int cliente_sk;
    public int tecnico_sk;
    public String fh_creacion;
    public String fh_cierre;
    public BigDecimal calificacion;
    public String descripcion;

    public int tipo_id;
    public String tipo;
    public int estado_id;
    public String estado_desc;

    public GestionItem toGestionItem() {
        GestionItem item = new GestionItem(ot_id,tipo,fh_creacion,estado_desc, descripcion);
        return  item;
    }
}
