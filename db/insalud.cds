namespace insalud;

using { cuid, sap.common.CodeList } from '@sap/cds/common';

using {Auditable as audit} from './common';

entity Patient: audit {
         key id: UUID;
            nif: String  @title: '{i18n>nif}';
    firsts_name: String  @title: '{i18n>firsts_name}';
      last_name: String  @title: '{i18n>last_name}';
        address: String  @title: '{i18n>address}';
    postal_code: String  @title: '{i18n>postal_code}';
           city: String  @title: '{i18n>city}';
         region: String  @title: '{i18n>region}';
        country: String  @title: '{i18n>country}';
   last_measure: Decimal(7,2) @title: '{i18n>last_measure}';
     tipo_aviso: Decimal(1);
      full_name: String(201) @(Core.Computed : true) = (firsts_name || ' ' || last_name);
       measures: Association to many Measure_Patient on measures.Patient = $self;
}

entity Measure_Patient: audit {
         key id: UUID;
        measure: Decimal(7,2) @title: '{i18n>measure}';
        Patient: Association to Patient @cds.foreignKey;
}
