package es.personal.mock.insalud.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO implements Serializable {

    private static final long serialVersionUID = 4587123196786539487L;

    private String nif;
    private String firsts_name;
    private String last_name;
    private String address;
    private String postal_code;
    private String city;
    private String region;
    private String country;
}
