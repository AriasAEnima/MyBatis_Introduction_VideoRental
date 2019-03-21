/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.test;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.quicktheories.core.Gen;
import org.quicktheories.generators.Generate;
import static org.quicktheories.generators.SourceDSL.dates;
import static org.quicktheories.generators.SourceDSL.integers;
import static org.quicktheories.generators.SourceDSL.lists;
import static org.quicktheories.generators.SourceDSL.longs;
import static org.quicktheories.generators.SourceDSL.strings;

/**
 *
 * @author julia
 */
public class Generadores {
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
        return longs().between(1L, 99L);
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
    
    public static Gen<String> descriptions(){
        return strings().betweenCodePoints(97, 122).ofLengthBetween(1,49).map((des)->new String(des)); 
    }
    
    public static Gen<ItemRentado> itemsRentados(){
        return source->{
            int id=integers().between(1, 20000).generate(source);
            Item it=items().generate(source);
            List<java.sql.Date> dts=twoDatestoRentSql().generate(source);              
            return new ItemRentado(id, it, dts.get(0), dts.get(1));
        };
        // new ItemRentado(0, item, fechainiciorenta, fechafinrenta)
    }
    public static Gen<Cliente> clientsWithItemsRent(){
        return clients().zip(listItemsRentados(), (c,Rentados)->{
            ArrayList<ItemRentado> n=new ArrayList<>();
            for (ItemRentado ir:Rentados){
                  n.add(ir);
            }
            c.setRentados(n);
        return c;});
    }
    
    public static Gen<List<ItemRentado>> listItemsRentados(){
        return lists().of(itemsRentados()).ofSizeBetween(1, 5);
    }
    
    public static Gen<Item> items(){
        return source->{
            TipoItem tipo=tipoitems().generate(source);
            int id=integers().between(1, 20000).generate(source);
            String nombre=names().generate(source);
            String descripcion=descriptions().generate(source);
            Date fechaLanzamiento=randomDate().generate(source);
            long tarifa=longs().between(0L, 5000L).generate(source);
            String formatoRenta=gendersOrFormat().generate(source);
            String genero=gendersOrFormat().generate(source);
            
            return new Item(tipo, id, nombre, descripcion, fechaLanzamiento, tarifa, formatoRenta, genero);
        }; 
        //new Item(tipo, 0, nombre, descripcion, fechaLanzamiento, 0, formatoRenta, genero)
    }
    
    public static Gen<TipoItem> tipoitems(){
       return integers().between(1,20).zip(descriptions(), (no,des)-> new TipoItem(no, des));
    }
    
    public static Gen<java.sql.Date> randomDate(){
        return dates().withMilliseconds(0).map((d)->new java.sql.Date(d.getTime()));
    }
    
    public static Gen<String> gendersOrFormat(){
        return strings().betweenCodePoints(97, 122).ofLengthBetween(4,10);
    }
    
    public static Gen<List<java.sql.Date>> twoDatestoRentSql(){
        return source->{
            List<java.sql.Date> ans=new ArrayList<java.sql.Date>();
            Date fechainicio=dates().withMilliseconds(0L).generate(source);            
            Calendar c = Calendar.getInstance();
            int delta=integers().between(0, 10).generate(source);
            c.setTime(fechainicio);       
            c.add(Calendar.DATE, delta);
            Date fechafinal=c.getTime();
            java.sql.Date sqlinicial=new java.sql.Date(fechainicio.getTime());
            java.sql.Date sqlfinal=new java.sql.Date(fechafinal.getTime());
            ans.add(sqlinicial);
            ans.add(sqlfinal);
            return ans;
        };
    }
    
    public static long diffdays(Date finicial,Date ffinal){     
       return finicial.getTime()-ffinal.getTime();       
    }
}
