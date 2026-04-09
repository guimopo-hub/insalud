package es.personal.mock.insalud.repository;

import es.personal.mock.insalud.beans.QueryObjectDTO;

import java.util.List;
import java.util.Map;

public interface InsaludRepository {
    List<?> findList(List<QueryObjectDTO> lstQueryObjectDTO, String entityName, Class entityClass);

    Object findOne(List<QueryObjectDTO> lstQueryObjectDTO, String entityName, Class entityClass);

    String save(String entityName, Map<String, ?> entity);
}
