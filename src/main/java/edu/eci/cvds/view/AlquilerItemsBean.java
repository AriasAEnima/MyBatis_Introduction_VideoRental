/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.view;

import com.google.inject.Inject;
import edu.eci.cvds.view.BasePageBean;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Andres Quintero
 */
@ManagedBean(name = "AlquilerItems")
@SessionScoped
public class AlquilerItemsBean extends BasePageBean implements Serializable {

   // ServiciosAlquiler sp = ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
    
    @Inject
    ServiciosAlquiler sp;
    
    public List<Cliente> listaClientes;
    public Cliente cliente;
    public ItemRentado itemRentado;
    public List<ItemRentado> listaRentados;
    public List<Item> listaItems;
    
    public String nombre;
    public long documento;
    public String telefono;
    public String direccion;
    public String email;
    
    
    public int itemId;
    public int dias;
    public long precio;
    public long multa;
    public Item it;
    
    public void rentarItem() throws ExcepcionServiciosAlquiler{
        it=sp.consultarItem(itemId);
        sp.registrarAlquilerCliente(java.sql.Date.valueOf(LocalDate.MAX), cliente.getDocumento(), it, dias);
        
    }

    public Item getItem() throws ExcepcionServiciosAlquiler {
        return sp.consultarItem(itemId);
    }

    public List<Item> getListaItems() {
        if (listaItems==null){
            try {
                listaItems=sp.consultarItemsDisponibles();
            } catch (ExcepcionServiciosAlquiler ex) {
                Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaItems;
    }

    public void setListaItems(List<Item> listaItems) {
        this.listaItems = listaItems;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public void setItem(Item item) {
        this.it = item;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public void consultaPrecio() throws ExcepcionServiciosAlquiler {
        //System.out.println(sp.consultarItemsDisponibles());
        
        //System.out.println("hasta aca");
        it= sp.consultarItem(itemId);
        precio=it.getTarifaxDia()*this.dias;
        //System.out.println(it.getNombre());
        //System.out.println(precio);
    }

    public Item getIt() {
        return it;
    }

    public void setIt(Item it) {
        this.it = it;
    }

    public int getDias() {
        return dias;
    }

    public long getMulta() {
        return multa;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setMulta(long multa) {
        this.multa = multa;
    }

    public AlquilerItemsBean() {
        this.cliente = new Cliente();     
    }

    public ItemRentado getItemRentado() {
        return itemRentado;
    }

    public void setItemRentado(ItemRentado itemRentado) {
        this.itemRentado = itemRentado;
    }

    public List<ItemRentado> getListaRentados() throws ExcepcionServiciosAlquiler {
        return sp.consultarItemsCliente(this.cliente.getDocumento());
    }

    public void setListaRentados(List<ItemRentado> listaItems) {
        this.listaRentados = listaItems;
    }
    
    public List<Cliente> getListaClientes() throws ExcepcionServiciosAlquiler {
        return sp.consultarClientes();
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void clienteNuevo() throws ExcepcionServiciosAlquiler{
        sp.registrarCliente(new Cliente(nombre,documento,telefono,direccion,email));
    }
    
    public void onRowSelect(SelectEvent event) {
        try {
            cliente=(Cliente) event.getObject();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().redirect("RegistroAlquiler.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
 
    public void onRowUnselect(UnselectEvent event) {
        cliente=null;
    }

}