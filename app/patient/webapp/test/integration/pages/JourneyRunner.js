sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"patient/test/integration/pages/PatientList",
	"patient/test/integration/pages/PatientObjectPage"
], function (JourneyRunner, PatientList, PatientObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('patient') + '/test/flpSandbox.html#patient-tile',
        pages: {
			onThePatientList: PatientList,
			onThePatientObjectPage: PatientObjectPage
        },
        async: true
    });

    return runner;
});

