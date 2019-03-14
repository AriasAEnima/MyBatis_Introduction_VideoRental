/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;
import java.util.List;

/**
 *
 * @author 2131381
 */
public interface ClienteDAO {
         

     public Cliente load(long dc) throws PersistenceException;
     
     public List<Cliente> loadAll() throws PersistenceException;
}
