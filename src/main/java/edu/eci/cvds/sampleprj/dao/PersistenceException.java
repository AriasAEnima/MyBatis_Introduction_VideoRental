/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.sampleprj.dao;

/**
 *
 * @author 2109117
 */
public class PersistenceException extends Exception {
    
    public PersistenceException(String string, org.apache.ibatis.exceptions.PersistenceException e) {
        super(string);
    }
    
}
