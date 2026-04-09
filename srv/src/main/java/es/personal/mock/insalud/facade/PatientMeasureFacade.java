package es.personal.mock.insalud.facade;

import java.math.BigDecimal;
import java.util.List;

public interface PatientMeasureFacade {
    List<cds.gen.insalud.MeasurePatient> findMeasuresByPatientId(String patiendId);

    void setMeasureByPatientId(String patientId, BigDecimal measure) throws Exception;
}
