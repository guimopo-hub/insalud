package es.personal.mock.insalud.facadeImpl;

import cds.gen.insalud.Patient;
import cds.gen.insalud.Patient_;
import es.personal.mock.insalud.beans.Operator;
import es.personal.mock.insalud.beans.QueryObjectDTO;
import es.personal.mock.insalud.facade.PatientFacade;
import es.personal.mock.insalud.repository.InsaludRepository;
import es.personal.mock.insalud.utils.InsaludUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientFacadeImpl implements PatientFacade {

    @Autowired
    private InsaludRepository insaludRepository;

    @Autowired
    private InsaludUtils utils;

    @Override
    public cds.gen.insalud.Patient getPatientById(String patientId) {
        List<QueryObjectDTO> lstQueryObjectDTO = null;
        lstQueryObjectDTO = utils.addElementToList(Patient.ID,
                patientId, Operator.EQUALS, lstQueryObjectDTO);
         return (Patient) insaludRepository.findOne(lstQueryObjectDTO, Patient_.CDS_NAME,Patient.class);
    }
}
