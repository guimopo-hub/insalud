package es.personal.mock.insalud.repositoryImpl;

import com.sap.cds.Result;
import com.sap.cds.Row;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.persistence.PersistenceService;
import es.personal.mock.insalud.beans.QueryObjectDTO;
import es.personal.mock.insalud.repository.InsaludRepository;
import es.personal.mock.insalud.utils.InsaludUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class InsaludRepositoryImpl implements InsaludRepository {


    @Autowired
    private InsaludUtils utils;

    @Autowired
    private PersistenceService persistenceService;

    @Override
    public List<?> findList(List<QueryObjectDTO> lstQueryObjectDTO, String entityName, Class entityClass) {
        CqnSelect query = utils.getQuery(lstQueryObjectDTO, entityName);
        Result result = persistenceService.run(query);
        List<?> lst = result.stream()
                .map(row -> row.as(entityClass))
                .collect(Collectors.toList());
        return lst;
    }

    @Override
    public Object findOne(List<QueryObjectDTO> lstQueryObjectDTO, String entityName, Class entityClass) {
        CqnSelect query = utils.getQuery(lstQueryObjectDTO, entityName);
        Result result = persistenceService.run(query);
        Object obj =
                result.stream().findFirst().map(row -> row.as(entityClass)).orElse(null);
        return obj;
    }

    @Override
    public String save(String entityName, Map<String, ?> entity) {
        Insert insert = Insert.into(entityName).entry(entity);
        Result resultInsert = persistenceService.run(insert);
        Row rowInsert = resultInsert.single();
        try {
            return utils.getStringFromRow(rowInsert, "id");
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
