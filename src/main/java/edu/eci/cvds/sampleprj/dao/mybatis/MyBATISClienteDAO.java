/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

/**
 *
 * @author 2131381
 */
public class MyBATISClienteDAO implements ClienteDAO{

    @Inject
    private ClienteMapper clienteMapper;    
   
   
    @Override
    public Cliente load(long dc) throws PersistenceException {
        Cliente cl=null;
        try{
            cl=clienteMapper.consultarCliente(dc);                    
        }catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al registrar el item "+dc,e);
        }   
        return cl;
    }

    @Override
    public List<Cliente> loadAll() throws PersistenceException{
         List<Cliente> ans=new ArrayList<>();
        try{
            ans=clienteMapper.consultarClientes();                    
        }catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("No hay Registros",e);
        }   
        return ans;
    }

    @Override
    public void rentItemtoClient(Date fechainicio,long dc,int idit,int days) throws PersistenceException {
         try{            
            Calendar c = Calendar.getInstance();
            c.setTime(fechainicio);
            c.add(Calendar.DATE, days);
            clienteMapper.agregarItemRentadoACliente(dc, idit, fechainicio, c.getTime());                  
        }catch(org.apache.ibatis.exceptions.PersistenceException e){
             throw new PersistenceException("No existe el cliente o el item",e);
        }   
        
    }

    @Override
    public void save(Cliente c) throws PersistenceException{
        try{
             clienteMapper.agregarCliente(c);
        }catch(org.apache.ibatis.exceptions.PersistenceException e){
            System.out.println("ERRRIR EN EL MAPEER=========00000000000");
             throw new PersistenceException("Cliente ya existe",e);
        }   
    }

    @Override
    public void setVetado(long doc,boolean v) throws PersistenceException {        
        try{
            clienteMapper.updateVetado(doc,v?1:0);                    
        }catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Item no existe",e);
        }        
    }
    
    
    
}
