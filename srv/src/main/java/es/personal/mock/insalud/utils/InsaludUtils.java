package es.personal.mock.insalud.utils;

import com.sap.cds.Row;
import com.sap.cds.ql.CQL;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.StructuredType;
import com.sap.cds.ql.Value;
import com.sap.cds.ql.cqn.CqnPredicate;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnSortSpecification;
import es.personal.mock.insalud.beans.Operator;
import es.personal.mock.insalud.beans.QueryObjectDTO;
import es.personal.mock.insalud.constant.InsaludConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InsaludUtils {


    public CqnSelect getQuery(List<QueryObjectDTO> lstQueryObjectDTO, String tableName) {
        //CqnSelect query;
        int limit = 0;
        long page = 0;
        boolean hasOrder = false;
        String fieldToOrder = null;
        String orderDescAsc = null;
        List<QueryObjectDTO> lstQueryObjectDTOLimit =
                lstQueryObjectDTO.stream().filter(p -> p.getOperator().equals(Operator.LIMIT)).toList();
        List<QueryObjectDTO> lstQueryObjectDTOPage =
                lstQueryObjectDTO.stream().filter(p -> p.getOperator().equals(Operator.PAGE)).toList();
        List<QueryObjectDTO> lstQueryObjectDTOOrderBy =
                lstQueryObjectDTO.stream().filter(p -> p.getOperator().equals(Operator.ORDER_BY)).toList();

        if (!lstQueryObjectDTOLimit.isEmpty() && lstQueryObjectDTOLimit.size() == 1) {
            limit = Integer.parseInt(lstQueryObjectDTOLimit.get(0).getColumnValue().toString());
        }
        if (!lstQueryObjectDTOPage.isEmpty() && lstQueryObjectDTOPage.size() == 1) {
            page = Integer.parseInt(lstQueryObjectDTOPage.get(0).getColumnValue().toString());
        }
        if (!lstQueryObjectDTOOrderBy.isEmpty() && lstQueryObjectDTOOrderBy.size() == 1) {
            hasOrder = true;
            fieldToOrder = lstQueryObjectDTOOrderBy.get(0).getColumnName();
            orderDescAsc = lstQueryObjectDTOOrderBy.get(0).getColumnValue().toString();
        }

        Select<?> selectBuilder = Select.from(tableName);
        // Where
        if (!lstQueryObjectDTO.isEmpty()) {
            selectBuilder = selectBuilder.where(p -> getPredicate(lstQueryObjectDTO, p));
        }
        // Order by
        if (hasOrder) {
            Value<?> orderField = CQL.get(fieldToOrder);
            CqnSortSpecification.Order order =
                    Operator.DESC.name().equals(orderDescAsc)
                            ? CqnSortSpecification.Order.DESC
                            : CqnSortSpecification.Order.ASC;
            selectBuilder = selectBuilder.orderBy(CQL.sort(orderField, order));
        }
        // Limit
        if (limit > 0) {
            selectBuilder = selectBuilder.limit(limit, page * limit);
        }
        return selectBuilder;
    }

    public CqnPredicate getPredicate(List<QueryObjectDTO> lstQueryObjectDTO, StructuredType<?> p) {
        List<QueryObjectDTO> lstQueryObjectOrDTO = lstQueryObjectDTO.stream().
                filter(q -> q.getComparator() != null &&
                        q.getComparator().name().equals(InsaludConstants.OPERATOR_OR)).toList();
        List<QueryObjectDTO> lstQueryObjectAndDTO = lstQueryObjectDTO.stream().
                filter(q -> q.getComparator() == null ||
                        q.getComparator().name().equals(InsaludConstants.OPERATOR_AND)).toList();
        CqnPredicate cqnPredicate = null;
        String key;
        String value;
        List<CqnPredicate> lstCqnPredicateAnd = new ArrayList<>();
        List<CqnPredicate> lstCqnPredicateOr = new ArrayList<>();
        for (QueryObjectDTO queryObjectDTO : lstQueryObjectAndDTO) {
            cqnPredicate = buildPredicate(queryObjectDTO, p);
            if (cqnPredicate != null) {
                lstCqnPredicateAnd.add(cqnPredicate);
            }
        }
        for (QueryObjectDTO queryObjectDTO : lstQueryObjectOrDTO) {
            cqnPredicate = buildPredicate(queryObjectDTO, p);
            if (cqnPredicate != null) {
                lstCqnPredicateOr.add(cqnPredicate);
            }
        }

        boolean hasOr = !lstCqnPredicateOr.isEmpty();
        boolean hasAnd = !lstCqnPredicateAnd.isEmpty();
        if (hasOr && hasAnd) {
            cqnPredicate = CQL.and(
                    CQL.or(lstCqnPredicateOr),
                    CQL.and(lstCqnPredicateAnd)
            );
        } else if (hasOr) {
            cqnPredicate = CQL.or(lstCqnPredicateOr);
        } else if (hasAnd) {
            cqnPredicate = CQL.and(lstCqnPredicateAnd);
        }


        return cqnPredicate;
    }

    private CqnPredicate buildPredicate(QueryObjectDTO queryObjectDTO, StructuredType<?> p) {
        String key;
        String value;
        key = queryObjectDTO.getColumnName();
        CqnPredicate cqnPredicate = null;
        if (queryObjectDTO.getColumnValue() == null) {
            return null;
        }
        value = queryObjectDTO.getColumnValue().toString();
        if (queryObjectDTO.getOperator().equals(Operator.EQUALS)) {
            cqnPredicate = p.get(key).eq(value);
        }
        if (queryObjectDTO.getOperator().equals(Operator.NOT_EQUALS)) {
            cqnPredicate = p.get(key).ne(value);
        }
        if (queryObjectDTO.getOperator().equals(Operator.IS_NOT_NULL)) {
            cqnPredicate = p.get(key).isNotNull();
        }
        if (queryObjectDTO.getOperator().equals(Operator.IS_NULL)) {
            cqnPredicate = p.get(key).isNull();
        }
        if (queryObjectDTO.getOperator().name().equals(InsaludConstants.OPERATOR_OR) ||
                queryObjectDTO.getOperator().name().equals(InsaludConstants.OPERATOR_AND)) {
            return null;
        }

        return cqnPredicate;
    }


    public List<QueryObjectDTO> addElementToList(String column, Object value, Operator operator,
                                                 List<QueryObjectDTO> lst) {
        if (lst == null || lst.isEmpty()) {
            lst = new ArrayList<>();
        }
        QueryObjectDTO queryObjectDTO;
        queryObjectDTO = new QueryObjectDTO();
        queryObjectDTO.setColumnName(column);
        queryObjectDTO.setColumnValue(value);
        queryObjectDTO.setOperator(operator);
        lst.add(queryObjectDTO);
        return lst;
    }

    public String getStringFromRow(Row row, String field) {
        if (row.get(field) != null) {
            return row.get(field).toString();
        } else {
            return "";
        }
    }

}
