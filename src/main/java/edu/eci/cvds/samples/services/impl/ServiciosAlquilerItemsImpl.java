package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import static edu.eci.cvds.sampleprj.dao.PersistenceException.*;
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
           throw new ExcepcionServiciosAlquiler(ITEM_NO_ENCONTRADO, ex);
       }
        return (int) it.getTarifaxDia();
   }

   @Override
   public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
       Cliente cl=null;
       try {
           cl=clienteDAO.load(docu);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(CLIENTE_NO_ENCONTRADO, ex);
       }
        return cl;
   }

   @Override
   public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
       Cliente cl=null;
       try {
           cl=clienteDAO.load(idcliente);           
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(CLIENTE_NO_ENCONTRADO, ex);
       }
        return cl.getRentados();
   }

   @Override
   public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
       List<Cliente> ans=new ArrayList<>();
       try {
           ans=clienteDAO.loadAll();           
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(SIN_REGISTRO, ex);
       }
        return ans;
   }

   @Override
   public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.load(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(ERROR_CONSULTAR_ITEM+id,ex);
       }
   }

   @Override
   public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler{
       try {
           return itemDAO.loadAll();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(ERROR_CONSULTAR_ITEMS,ex);
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
           throw new ExcepcionServiciosAlquiler(ITEM_NO_EXISTE);       
       
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
           throw new ExcepcionServiciosAlquiler(ERROR_TIPOS,ex);
       }
   }

   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {     
       try {
           clienteDAO.rentItemtoClient(docu, item.getId(),numdias);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(CLIENTE_ITEM_NO_ENCONTRADO, ex);
       }      
   }

   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
      try {
           clienteDAO.save(c);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(CLIENTE_ITEM_NO_ENCONTRADO, ex);
       }  
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.load(iditem).getTarifaxDia()*numdias;
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(ITEM_NO_ENCONTRADO,ex);
       }
   }

   @Override
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       try {
           itemDAO.updateTarifa(id, tarifa);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(ITEM_NO_ENCONTRADO,ex);
       }
   }
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try {
           itemDAO.save(i);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(NO_FK,ex);
       }
   }

   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        try {
           clienteDAO.setVetado(docu, estado);
        }catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler(CLIENTE_NO_ENCONTRADO,ex);
        }
   }
}