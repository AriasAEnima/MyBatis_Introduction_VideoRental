package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

@Singleton
public class ServiciosAlquilerItemsImpl implements ServiciosAlquiler {

   @Inject
   private ItemDAO itemDAO;
   @Inject 
   private ClienteDAO clienteDAO;
   @Inject
   private TipoItemDAO tipoitemDAO;
   

   @Override
   public int valorMultaRetrasoxDia(int itemId) throws ExcepcionServiciosAlquiler {
       Item it=null;
       try {
           it=itemDAO.load(itemId);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Item no encontrado", ex);
       }
        return (int) it.getTarifaxDia();
   }

   @Override
   public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
       Cliente cl=null;
       try {
           cl=clienteDAO.load(docu);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Cliente no encontrado", ex);
       }
        return cl;
   }

   @Override
   public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
       Cliente cl=null;
       try {
           cl=clienteDAO.load(idcliente);           
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Cliente no encontrado", ex);
       }
        return cl.getRentados();
   }

   @Override
   public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
       List<Cliente> ans=new ArrayList<>();
       try {
           ans=clienteDAO.loadAll();           
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Sin Registros", ex);
       }
        return ans;
   }

   @Override
   public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.load(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
       }
   }

   @Override
   public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler{
       try {
           return itemDAO.loadAll();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar los items ",ex);
       }
   }

   @Override
   public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
       ItemRentado fc=null;
       long ans=0L;
       for (Cliente c:consultarClientes()){
           for (ItemRentado ir: c.getRentados()){
               if(ir.getId()==iditem){
                    fc=ir;
               }
           }
       }
       if (fc==null)
           throw new ExcepcionServiciosAlquiler("Item no esta rentado o no existe");       
       
       long diff = fechaDevolucion.getTime()-fc.getFechafinrenta().getTime();
       
       if (diff<=0){
           ans=0L;
       }else{
           long days =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
           ans= fc.getItem().getTarifaxDia()*days;
       }      
       return ans;       
   }

   @Override
   public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
       return consultarItem(id).getTipo();
   }

   @Override
   public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
       try {
           return tipoitemDAO.loadAll();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar los tipos",ex);
       }
   }

   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {     
       try {
           clienteDAO.rentItemtoClient(docu, item.getId(),numdias);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("No existe el item o no existe el cliente", ex);
       }      
   }

   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
      try {
           clienteDAO.addCliente(c);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("No existe el item o no existe el cliente", ex);
       }  
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.load(iditem).getTarifaxDia()*numdias;
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("No se encuentra el item ",ex);
       }
   }

   @Override
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       try {
           itemDAO.updateTarifa(id, tarifa);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("No se encuentra el item ",ex);
       }
   }
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try {
           itemDAO.save(i);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error de datos , posiblemente tipo no existe ",ex);
       }
   }

   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        try {
           clienteDAO.setVetado(docu, estado);
        }catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Cliente no encontrado ",ex);
        }
   }
}