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
    
    public static final String NO_FK = "Error de datos , posiblemente tipo no existe.";
    public static final String ERROR_REGISTRAR_ITEM = "Error al registrar item.";
    public static final String ITEM_NO_ENCONTRADO = "No se encontro el item.";
    public static final String CLIENTE_NO_ENCONTRADO = "No se encontro el cliente.";
    public static final String CLIENTE_ITEM_NO_ENCONTRADO = "No existe el cliente o no existe el item";
    public static final String ERROR_TIPOS = "Error al consultar los tipos";
    public static final String ITEM_NO_EXISTE = "Item no esta rentado o no existe";
    public static final String ERROR_CONSULTAR_ITEMS = "Error al consultar items";
    public static final String ERROR_CONSULTAR_ITEM = "Error al consultar item";
    public static final String SIN_REGISTRO = "Sin registros";
    public PersistenceException(String string, org.apache.ibatis.exceptions.PersistenceException e) {
        super(string);
    }
    
}
