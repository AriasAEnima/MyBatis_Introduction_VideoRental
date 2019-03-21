/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.samples.services.client;



import edu.eci.cvds.sampleprj.dao.mybatis.mappers.*;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author hcadavid
 */
public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
       

        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        ClienteMapper cm=sqlss.getMapper(ClienteMapper.class);
        System.out.println(cm.consultarClientes());   
        System.out.println("=================================");  
        System.out.println(cm.consultarCliente(123456));
        System.out.println("=================================");  
        ItemMapper im=sqlss.getMapper(ItemMapper.class);
        
        System.out.println(im.consultarItems());
        
        //TipoItem ti=new TipoItem(1, "Bla bla");
        //Item it=new Item(ti, 85, "Mi primera puñalada", "Pelicula con grandes enseñanzas", new java.sql.Date(0L), 5000, "tdb", "infantil");
        //im.insertarItem(it);
        //Cliente cl=new Cliente("Eduardo", 4, "123sss456", "Aqu8i", "emailsk@email.com");
        //cm.agregarCliente(cl);
        //cm.agregarItemRentadoACliente(8588, 40, new Date(), new Date());
        //Cliente cl=cm.consultarCliente(8588);        
        
        System.out.println(" De nuevo yo"+cm.consultarCliente(4));
        
       
        
        sqlss.commit();       
        sqlss.close();

        
        
    }


}
