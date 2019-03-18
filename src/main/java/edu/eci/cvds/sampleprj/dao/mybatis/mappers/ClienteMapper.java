package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;

/**
 *
 * @author 2106913
 */
public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param("idcli") long id); 
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param id
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
     public void agregarItemRentadoACliente(@Param("doc") long id,
            @Param("idit")int idit, 
            @Param("fiir")Date fechainicio,
            @Param("ffir")Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */
    public List<Cliente> consultarClientes();
    
    public void agregarCliente(@Param("doc") long doc, 
            @Param("nombre") String nombre, @Param("telefono") String telefono,
            @Param("direccion") String direccion, @Param("email") String email,
            @Param("vetado") int vetado);

    public void updateVetado(@Param("doc") long doc,@Param("vetado")int vet);
    
}
