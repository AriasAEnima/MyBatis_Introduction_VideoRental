package edu.eci.cvds.test;
import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import static edu.eci.cvds.test.Generadores.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.integers;

public class ServiciosAlquilerTest {

@Inject
private SqlSession sqlSession;


private  List<Long> usedDocs;

private  List<Integer> usedIdItems;
private  List<Integer> usedIdTypes;
private  ServiciosAlquiler serviciosAlquiler;
private  List<Cliente> clientesfill;

    public ServiciosAlquilerTest() {       
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
        
    }   
    
    @Before
    public void setUp(){
        usedIdTypes=new ArrayList<>();
        usedDocs=new ArrayList<>();
        usedIdItems=new ArrayList<>();      
        clientesfill=new ArrayList<>();
        fillClients();        
    }
 
    
    
    
    @Test
    public void consultarTiposTest(){  
         for (int itd: usedIdTypes){
            try {
                serviciosAlquiler.consultarTipoItem(itd);
            } catch (ExcepcionServiciosAlquiler ex) {
                System.out.println(ex.getMessage());
            }
        }         
    }
    
    @Test
    public void consultarItemsRentadosClienteTest(){
        for (long doc:usedDocs){
            try {
                int db=serviciosAlquiler.consultarItemsCliente(doc).size();
                int cu=findClient(doc).getRentados().size();
                assertTrue("El cliente no tiene la misma cantidad de items",db==cu);
            } catch (ExcepcionServiciosAlquiler ex) {
                System.out.println(ex.getMessage());
            }
        }        
    }
    
    @Test 
    public void consultaCostoAlquierTest(){
        for (int idt: usedIdItems){               
            qt().forAll(integers().between(1,20)).check((n)->{
                boolean ans=true;
                 try {
                    Item i = serviciosAlquiler.consultarItem(idt);
                    long valor=i.getTarifaxDia();
                    long resl=serviciosAlquiler.consultarCostoAlquiler(i.getId(), n);
                    ans=valor*n==resl;
                 } catch (ExcepcionServiciosAlquiler ex) {
                     System.out.println(ex.getMessage());
                     ans=false;
                 }
                 return ans;});
        }      
    }
  
    
        
    @Test
    public void consultarItemsTest(){  
        for (int itd:usedIdItems){
            try {
                serviciosAlquiler.consultarItem(itd);
            } catch (ExcepcionServiciosAlquiler ex) {
                System.out.println(ex.getMessage());
            }
        }    
    }
    
     @Test
    public void consultarClientesTest(){  
        for (long doc:usedDocs){
            try {
                serviciosAlquiler.consultarCliente(doc);
            } catch (ExcepcionServiciosAlquiler ex) {
                System.out.println(ex.getMessage());
            }
        }        
    }
    
    
   
    
    
    
   
    
    
    

    
 ////////////      //////
    
/// TODO LO DE ABAJO PRUEBA LOS METODOS DE REGRISTRAR SE PRUEBA COMO SETUP!! 
    
 ////////////      ///////
    
     private Cliente findClient(long doc){
        Cliente ans=null;
        for (Cliente c:clientesfill){
            if (c.getDocumento()==doc){
                ans=c;
            }
        }
        return ans;
    }
    private void fillType(TipoItem ti) throws ExcepcionServiciosAlquiler{
        if (!usedIdTypes.contains(ti.getID())){            
            serviciosAlquiler.registrarTipo(ti);
            usedIdTypes.add(ti.getID());
        }       
     
    }
    
    private void fillItem(Item i) throws ExcepcionServiciosAlquiler{
        if (!usedIdItems.contains(i.getId())){
            fillType(i.getTipo());
            usedIdItems.add(i.getId());
            serviciosAlquiler.registrarItem(i);
            serviciosAlquiler.consultarItem(i.getId());
        }             
    }   
    
    
    
    private void fillRent(Cliente cl) throws ExcepcionServiciosAlquiler{
        List<ItemRentado> irs=cl.getRentados();
        for (ItemRentado ir:irs){
            fillItem(ir.getItem());
            serviciosAlquiler.registrarAlquilerCliente(new java.sql.Date(0L), cl.getDocumento(), ir.getItem(), 5);
        }
    }
    
    private void fillRents(){
        for(Cliente c:clientesfill){
            try {                
                fillRent(c);
            } catch (ExcepcionServiciosAlquiler ex) {
                 System.out.println(ex.getMessage());
            }
            
        }
    }
    private void fillClients() {
        qt().forAll(clientsWithItemsRent()).check((cliente)->{
            boolean ans=false;
            try {                   
                if (!usedDocs.contains(cliente.getDocumento())){    
                    serviciosAlquiler.registrarCliente(cliente);
                    clientesfill.add(cliente);
                    usedDocs.add(cliente.getDocumento());                                  
                }         
                 ans=true;  
              
            } catch (ExcepcionServiciosAlquiler ex) {
                ans=false;
                System.out.println(ex.getMessage());
            }                       
        
            return ans;});
        fillRents();
    }
    
    
   
    
   
}