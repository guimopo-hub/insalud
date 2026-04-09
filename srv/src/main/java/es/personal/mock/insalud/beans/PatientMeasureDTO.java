package es.personal.mock.insalud.beans;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PatientMeasureDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 458712394871239487L;

    private String patientId;

    private BigDecimal measure;
}
