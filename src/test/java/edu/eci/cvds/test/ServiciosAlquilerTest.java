package edu.eci.cvds.test;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.quicktheories.core.Gen;
import static org.quicktheories.QuickTheory.qt;
import org.quicktheories.generators.Generate;
import static org.quicktheories.generators.Generate.*;
import static org.quicktheories.generators.SourceDSL.*;

public class ServiciosAlquilerTest {

    @Inject
    private SqlSession sqlSession;

    ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {       
         serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Before
    public void setUp() {
        for (int i=1;i<=1000;i++){
            Cliente c=new Cliente("Nombre"+i, i, ""+(1000+i), 
                    "Calle "+(10+i)+" Carrera "+(11+i), "nombrez"+i+"@gmail.com");
            try {
                serviciosAlquiler.registrarCliente(c);
            } catch (ExcepcionServiciosAlquiler ex) {
                System.out.println("Error creando clientes pruebas");
            }
        }
    }   

    @Test
    public void emptyDB() {
        
       // System.out.println("Creado el servicios alquiler ========");
        
        //System.out.println("Creado el cliente ========");
        try {            
            for (int i=1;i<=1000;i++){
                serviciosAlquiler.consultarCliente((long) i);
                assertTrue(true);
            }         
           
        } catch (ExcepcionServiciosAlquiler ex) {
            System.out.println("Error consultando clientes pruebas");
        }       
    }
    
    public static Gen<Cliente> clients(){
        return source->{
            String nombre=names().generate(source);
            long documento=documents().generate(source);
            String telefono=phones().generate(source);
            String direccion=dirs().generate(source);
            String email=emails().generate(source);
            return new Cliente(nombre, documento, telefono, direccion, email);
        };
    }
    
    public static Gen<String> names(){
        return strings().betweenCodePoints(97, 122).ofLengthBetween(4,10).zip(
                Generate.constant(" "),(nombre,espacio)-> new String(nombre+espacio))
                .zip(strings().betweenCodePoints(97, 122).ofLengthBetween(4,10),(name,apellido) -> new String(name+apellido));
    }
    
    public static Gen<Long> documents(){
        return longs().between(1L, 9999999L);
    } 
    
    public static Gen<String> phones(){
        return integers().between(100000,9999999).map(numero->new String(""+numero));
    }
    
    public static Gen<String> dirs(){
        return integers().between(1, 200).zip(
                Generate.constant("Carrera "),(numero,carrera)-> new String(carrera+numero))
                .zip(Generate.constant("Calle "),(carrerano,calle)->new String(carrerano+calle))
                .zip(integers().between(1, 80),(resto,no)-> new String(resto+no));
    }
    
    public static Gen<String> emails(){
        return strings().betweenCodePoints(97, 122).ofLengthBetween(4,10).zip(
                Generate.constant("@gmail.com"),(nombre,arroba)-> new String(nombre+arroba));
    }
}