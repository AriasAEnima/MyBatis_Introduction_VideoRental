/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import java.util.List;

/**
 *
 * @author 2131381
 */
public interface TipoItemDAO {
    
    public List<TipoItem> loadAll() throws PersistenceException;

    public void save(TipoItem ti) throws PersistenceException;

    public TipoItem load(int id) throws PersistenceException;
}
