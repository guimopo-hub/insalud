package es.personal.mock.insalud.controller;

import es.personal.mock.insalud.beans.PatientDTO;
import es.personal.mock.insalud.beans.PatientMeasureDTO;
import es.personal.mock.insalud.facade.PatientFacade;
import es.personal.mock.insalud.facade.PatientMeasureFacade;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Log4j2
@RequestMapping(value = "/measure")
public class InsaludRestController {

    @Autowired
    private PatientMeasureFacade patientMeasureFacade;

    @Autowired
    private PatientFacade patientFacade;

    @PostMapping("/")
    public ResponseEntity<String> setMeasure(@RequestBody PatientMeasureDTO patientMeasureDTO) throws Exception {
        try {
            patientMeasureFacade.setMeasureByPatientId(patientMeasureDTO.getPatientId(), patientMeasureDTO.getMeasure());
            return ResponseEntity.ok("Medida registrada correctamente");
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al procesar la medida: " + e.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}")
    public PatientDTO getPatient(@PathVariable ("patientId") String patientId) throws Exception {
        try {
            cds.gen.insalud.Patient patient = patientFacade.getPatientById(patientId);
            PatientDTO patientDTO = new PatientDTO();
            BeanUtils.copyProperties(patient, patientDTO);
            patientDTO.setFirsts_name(patient.getFirstsName());
            patientDTO.setLast_name(patient.getLastName());
            patientDTO.setPostal_code(patient.getPostalCode());
            return patientDTO;
        }catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
