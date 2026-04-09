package es.personal.mock.insalud.beans;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QueryObjectDTO implements Serializable {

    private String columnName;

    private Object columnValue;

    private Operator operator;

    private Operator comparator;


}
