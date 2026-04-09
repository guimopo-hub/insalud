package es.personal.mock.insalud.handler;

import cds.gen.insaludservice.InsaludService_;
import cds.gen.insaludservice.Patient_;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import es.personal.mock.insalud.facade.PatientMeasureFacade;
import es.personal.mock.insalud.repository.InsaludRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Component
@ServiceName(InsaludService_.CDS_NAME)
@Log4j2
public class PatientHandler implements EventHandler {

    @Autowired
    private PatientMeasureFacade patientMeasureFacade;


    @After(event = CqnService.EVENT_READ, entity = Patient_.CDS_NAME)
    public void onBeforeRead(CdsReadEventContext context, List<cds.gen.insalud.Patient> lstPatient) throws Exception {
        log.info("onBeforeRead || IN");
        for (cds.gen.insalud.Patient patient : lstPatient) {
            List<cds.gen.insalud.MeasurePatient> lstMeasure = patientMeasureFacade.findMeasuresByPatientId(patient.getId());
            if (lstMeasure.isEmpty()) {
                continue;
            }
            lstMeasure.sort(Comparator.comparing(cds.gen.insalud.MeasurePatient::getCreatedAt).reversed());
            BigDecimal lastMeasure = lstMeasure.get(0).getMeasure();
            int enRango;
            if (lastMeasure == null) {
                enRango = -1;
            } else if (lastMeasure.compareTo(BigDecimal.valueOf(5)) <= 0) {
                enRango = 0;
            } else if (lastMeasure.compareTo(BigDecimal.valueOf(10)) <= 0) {
                enRango = 1;
            } else {
                enRango = 2;
            }
            patient.setLastMeasure(lastMeasure);
            patient.setTipoAviso(new BigDecimal(enRango));
            patient.setMeasures(lstMeasure);
        }
        log.info("onBeforeRead || OUT");
    }


}
