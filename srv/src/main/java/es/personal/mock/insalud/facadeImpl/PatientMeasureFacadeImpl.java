package es.personal.mock.insalud.facadeImpl;

import cds.gen.insalud.MeasurePatient;
import cds.gen.insalud.MeasurePatient_;
import cds.gen.insalud.Patient;
import es.personal.mock.insalud.beans.Operator;
import es.personal.mock.insalud.beans.QueryObjectDTO;
import es.personal.mock.insalud.facade.PatientMeasureFacade;
import es.personal.mock.insalud.repository.InsaludRepository;
import es.personal.mock.insalud.utils.InsaludUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
public class PatientMeasureFacadeImpl implements PatientMeasureFacade {

    @Autowired
    private InsaludRepository insaludRepository;

    @Autowired
    private InsaludUtils utils;

    @Override
    public List<cds.gen.insalud.MeasurePatient> findMeasuresByPatientId(String patientId){
        List<QueryObjectDTO> lstQueryObjectDTO = null;
        lstQueryObjectDTO = utils.addElementToList(MeasurePatient.PATIENT_ID,
                patientId, Operator.EQUALS, lstQueryObjectDTO);
        lstQueryObjectDTO = utils.addElementToList(MeasurePatient.CREATED_AT,
                Operator.DESC, Operator.ORDER_BY, lstQueryObjectDTO);
       return (List<cds.gen.insalud.MeasurePatient> )
               insaludRepository.
                       findList(lstQueryObjectDTO, cds.gen.insalud.MeasurePatient_.CDS_NAME,MeasurePatient.class);
    }

    @Override
    public void setMeasureByPatientId(String patientId, BigDecimal measure) throws Exception {
        List<QueryObjectDTO> lstQueryObjectDTO = null;
        lstQueryObjectDTO = utils.addElementToList(Patient.ID,
                patientId, Operator.EQUALS, lstQueryObjectDTO);
        cds.gen.insalud.Patient patient = (Patient)
                insaludRepository.
                        findOne(lstQueryObjectDTO, cds.gen.insalud.Patient_.CDS_NAME, cds.gen.insalud.Patient.class);
        if (patient == null) {
            throw new Exception("Patient not found");
        }

        MeasurePatient measurePatient = MeasurePatient.create();
        measurePatient.setPatientId(patientId);
        measurePatient.setMeasure(measure);
        measurePatient.setCreatedAt(Instant.now());
        measurePatient.setCreatedBy("admin");
        insaludRepository.save(MeasurePatient_.CDS_NAME, measurePatient);
    }
}
