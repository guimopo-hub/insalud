using { insalud as model} from '../db/insalud';

@path: 'InsaludService'
service InsaludService @(requires: 'any'){
   entity Patient as projection on model.Patient ;
   entity Measure_Patient as projection on model.Measure_Patient;

}