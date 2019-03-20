/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import java.util.List;


/**
 *
 * @author 2131381
 */
public class MyBATISTipoItemDAO implements TipoItemDAO{
    @Inject
    TipoItemMapper tipoItemMapper;
    private String it;

    @Override
    public List<TipoItem> loadAll() throws PersistenceException {
        try{
            return tipoItemMapper.consultarTiposItems();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar los tipos",e);
        }
    }

    @Override
    public void save(TipoItem ti) throws PersistenceException {
       try{
           tipoItemMapper.agregarTipoItem(ti);
           if(ti.getDescripcion()==null){
               System.out.println("NO ESTA NULOOOOOOOO ====================");
           }
       }
       catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar los tipos",e);
       }
    }

    @Override
    public TipoItem load(int id) throws PersistenceException {
        try{
            System.out.println("Llego a MYbatis tipo item DAO");
            return tipoItemMapper.consultarTipo(112345);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            System.out.println("Se estalla aqui");
            throw new PersistenceException("Error al consultar los tipos",e);
        }
    }

  
}
