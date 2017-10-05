package ar.com.netmefy.netmefy.adapters.elements;

import java.util.Date;

/**
 * Created by ignac on 4/10/2017.
 */

public class NotificationItem {
    private Long id;
    private String title;
    private String descripcion;
    private Date fecha;
    private int resId;
    private Boolean read;
    private String ordenDeTrabajo;

    public NotificationItem() {
    }

    public NotificationItem(Long id,String title, String descripcion, Date fecha, int resId, Boolean read, String ordenDeTrabajo) {
        this.id = id;
        this.title = title;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.resId = resId;
        this.read = read;
        this.ordenDeTrabajo = ordenDeTrabajo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getOrdenDeTrabajo() {
        return ordenDeTrabajo;
    }

    public void setOrdenDeTrabajo(String ordenDeTrabajo) {
        this.ordenDeTrabajo = ordenDeTrabajo;
    }
}
