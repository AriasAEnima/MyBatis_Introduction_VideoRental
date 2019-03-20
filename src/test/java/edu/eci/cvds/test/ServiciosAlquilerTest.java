package edu.eci.cvds.test;
import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import static edu.eci.cvds.test.Generadores.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.integers;

public class ServiciosAlquilerTest {

@Inject
private SqlSession sqlSession;

private static List<Long> usedDocs;
private static List<Item> usedItems;
private static List<Integer> usedTypes;
private static ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {       
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }   
    
    @Test
    public void agregarTipoTest(){
       qt().forAll(tipoitems()).check((tipoit)->{
            boolean ans=false;
            if(tipoit.getDescripcion()!=null){
                try {
                    serviciosAlquiler.registrarTipo(tipoit);
                } catch (ExcepcionServiciosAlquiler ex) {
                    System.out.println(ex.getMessage());
                }
                ans=true;
            }
           
            return true;});
        try {
            System.out.println("get "+serviciosAlquiler.consultarTipoItem(112345));
        } catch (ExcepcionServiciosAlquiler ex) {
            System.out.println(ex.getMessage());
        }
    
      
    }
    
    /**
     * 
     * Test of Registrar Cliente , Registrar Item , Registrar Alquiler Cliente
     * 
     
    static{
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
        usedDocs=new ArrayList<Long>();
        usedItems=new ArrayList<Item>();    
        usedTypes=new ArrayList<Integer>();   
        qt().forAll(clients()).check((cliente)->{
            boolean ans=false;
            try {
                serviciosAlquiler.registrarCliente(cliente);
                ans=true;
                if (!usedDocs.contains(cliente.getDocumento()))
                    usedDocs.add(cliente.getDocumento());
             
            } catch (ExcepcionServiciosAlquiler ex) {
                System.out.println("ERRROR");
            }           
        
            return ans;});
         qt().forAll(items()).check((item)->{
            boolean ans=false;
          
            try {
               
                serviciosAlquiler.registrarTipo(item.getTipo());
                serviciosAlquiler.registrarItem(item);                
                usedTypes.add(item.getTipo().getID());
                
                ans=true;
              
                usedItems.add(item);
               
            } catch (ExcepcionServiciosAlquiler ex) {
                //               
            }                   
            return ans;});    
         try{
            System.out.println("####"+serviciosAlquiler.consultarTiposItem());
            TipoItem ti=serviciosAlquiler.consultarTipoItem(112345);           
            System.out.println("##///##"+serviciosAlquiler.consultarItemsDisponibles());
         }catch(Exception e){
                 System.out.println(e.getMessage());
         }
        
         qt().forAll(itemsRentados(),
                 integers().between(0, usedDocs.size()-1),
                 integers().between(0, 10)
                ).check((itr,docpick,days)->{    
                    boolean ans=false;
                    try {                    
                        serviciosAlquiler
                                .registrarAlquilerCliente(itr.getFechainiciorenta(), 
                                        usedDocs.get(docpick), itr.getItem(), days);
                        ans=true;
                    } catch (ExcepcionServiciosAlquiler ex) {
                        //
                    }
                return ans;});      
    }   
    

    //@Test
    public void ConsultarClientesTest() throws ExcepcionServiciosAlquiler {
        qt().forAll(integers().between(0, usedDocs.size()-1)).check((index)->{
            boolean ans=false;
            try {
                serviciosAlquiler.consultarCliente(usedDocs.get(index));
                ans=true;
            } catch (ExcepcionServiciosAlquiler ex) {
                //
            }     
            
            return ans;
        });
    }
    
   // @Test
    public void ConsultarTodosClientesTest(){
        try {
           
            assertTrue(serviciosAlquiler.consultarClientes().size()==usedDocs.size());
        } catch (ExcepcionServiciosAlquiler ex) {
            //
        }
        //forzar();
    }
    
    
    //@Test
    public void ConsultarItemsDisponibles(){
        try {
            System.out.println(serviciosAlquiler.consultarItemsDisponibles().size()+"==="+usedItems.size());
            assertTrue(serviciosAlquiler.consultarItemsDisponibles().size()==usedItems.size());
             
        } catch (ExcepcionServiciosAlquiler ex) {
            //
        }
    }
      /*String fs=String.format("FALLO CON: %s,%s,%s,%s,%s,%s",
                        ""+item.getId(),
                        ""+item.getTipo(),
                        ""+item.getNombre(),
                        ""+item.getDescripcion(),
                        ""+item.getFechaLanzamiento(),
                        ""+item.getTarifaxDia(),
                        ""+item.getFormatoRenta(),
                        ""+item.getGenero()
                );
                System.out.println("==================");
                System.out.println(fs);
                System.out.println("==================");
                System.out.print(ex.getMessage());
      
           
    
    private void forzar(){
        Cliente p=new Cliente("asd", usedDocs.get(0), "123134", "sda", "asda");
        try {
                int antes=serviciosAlquiler.consultarClientes().size();        
                serviciosAlquiler.registrarCliente(p);
                System.out.println(antes +">"+serviciosAlquiler.consultarClientes().size());
                System.out.println("==============");
                for (int i=0; i<=10 ; i++){
                    p=new Cliente("asd", 999999999L+i, "123", "sda", "asda");
                    antes=serviciosAlquiler.consultarClientes().size();        
                    serviciosAlquiler.registrarCliente(p);
                    System.out.println(serviciosAlquiler.consultarClientes().size() +">"+antes);
                }
                 System.out.println("======NO INSERT ===");

                for (int i=0; i<=10 ; i++){
                    p=new Cliente("asd", usedDocs.get(i), "123", "sda", "asda");
                    antes=serviciosAlquiler.consultarClientes().size();        
                    serviciosAlquiler.registrarCliente(p);
                    System.out.println(serviciosAlquiler.consultarClientes().size() +">"+antes);
                } 
        
      
            } catch (ExcepcionServiciosAlquiler ex) {
                System.out.println("No ocurrio");
            }
    }*/
    
    
   
}