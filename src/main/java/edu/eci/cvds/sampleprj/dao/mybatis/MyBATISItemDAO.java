package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;
import java.sql.SQLException;
import java.util.List;

public class MyBATISItemDAO implements ItemDAO{

  @Inject
  private ItemMapper itemMapper;    

  @Override
  public void save(Item it) throws PersistenceException{
    try{
        itemMapper.insertarItem(it);
        if (it.getTipo()==null){
            System.out.println("TIPO EN ITEM ES NULOOOOOOOO A!!!!!!");
        }
    }
    catch(org.apache.ibatis.exceptions.PersistenceException e){
        throw new PersistenceException(e.getMessage()+it.toString(),e);
    }        

  }

  @Override
  public Item load(int id) throws PersistenceException {
        try{
            return itemMapper.consultarItem(id);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException(e.getMessage(),e);
        }

    }

    @Override
    public List<Item> loadAll() throws PersistenceException {
        try{
            return itemMapper.consultarItems();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar los items ",e);
        }
    }

    @Override
    public void updateTarifa(int id, long nuevatarifa) throws PersistenceException {
         try{
            itemMapper.updateTarifa(id, nuevatarifa);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar los items ",e);
        }
    }

   

  }