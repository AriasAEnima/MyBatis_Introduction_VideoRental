package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import edu.eci.cvds.samples.entities.Item;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.TipoItem;

public interface TipoItemMapper {
    
    
    public List<TipoItem> consultarTiposItems();
    
    public TipoItem consultarTipo(@Param("id") int id);    

    public void agregarTipoItem(@Param("ti") TipoItem t);
    

}
