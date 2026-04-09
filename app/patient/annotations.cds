using InsaludService as service from '../../srv/insalud_services';
annotate service.Patient with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : created_at,
            },
            {
                $Type : 'UI.DataField',
                Value : created_by,
            },
            {
                $Type : 'UI.DataField',
                Value : modified_at,
            },
            {
                $Type : 'UI.DataField',
                Value : modified_by,
            },
            {
                $Type : 'UI.DataField',
                Value : nif,
            },
            {
                $Type : 'UI.DataField',
                Value : firsts_name,
            },
            {
                $Type : 'UI.DataField',
                Value : last_name,
            },
            {
                $Type : 'UI.DataField',
                Value : address,
            },
            {
                $Type : 'UI.DataField',
                Value : postal_code,
            },
            {
                $Type : 'UI.DataField',
                Value : city,
            },
            {
                $Type : 'UI.DataField',
                Value : region,
            },
            {
                $Type : 'UI.DataField',
                Value : country,
            },
        ],
    },
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'GeneratedFacet1',
            Label : 'General Information',
            Target : '@UI.FieldGroup#GeneratedGroup',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>Measures}',
            ID : 'i18nMeasures',
            Target : 'measures/@UI.LineItem#i18nMeasures',
        },
    ],
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : firsts_name,
        },
        {
            $Type : 'UI.DataField',
            Value : last_name,
        },
        {
            $Type : 'UI.DataField',
            Value : nif,
        },
        {
            $Type : 'UI.DataField',
            Value : address,
        },
        {
            $Type : 'UI.DataField',
            Value : city,
        },
        {
            $Type : 'UI.DataField',
            Value : postal_code,
        },
        {
            $Type : 'UI.DataField',
            Value : region,
        },
        {
            $Type : 'UI.DataField',
            Value : country,
        },
        {
            $Type : 'UI.DataField',
            Value : last_measure,
            Criticality : {
                $edmJson : {
                    $If : [
                        { $Eq : [ { $Path : 'tipo_aviso' }, '0' ] }, 3,
                        { $If : [
                            { $Eq : [ { $Path : 'tipo_aviso' }, '1' ] }, 2,1
                        ]}
                    ]
                }
            }
        },
    ],
    UI.SelectionFields : [
        address,
        city,
        country,
        created_at,
        firsts_name,
        last_name,
        nif,
        postal_code,
        region,
    ],
    UI.FieldGroup #i18nMeasures : {
        $Type : 'UI.FieldGroupType',
        Data : [
        ],
    },
    UI.HeaderInfo : {
        Title : {
            $Type : 'UI.DataField',
            Value : full_name,
        },
        TypeName : '',
        TypeNamePlural : '',
        TypeImageUrl : 'sap-icon://wounds-doc',
    },
    UI.Identification : [
        {
            $Type : 'UI.DataField',
            Value : address,
        },
    ],
    UI.DataPoint #last_measure : {
        $Type : 'UI.DataPointType',
        Value : last_measure,
        Title : '{i18n>last_measure}',
    },
    UI.HeaderFacets : [
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'last_measure',
            Target : '@UI.DataPoint#last_measure',
        },
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'last_measure3',
            Target : '@UI.Chart#last_measure',
        },
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'measure1',
            Target : 'measures/@UI.Chart#measure4',
        },
    ],
    UI.DataPoint #progress : {
        $Type : 'UI.DataPointType',
        Value : last_measure,
        Title : '{i18n>last_measure}',
        TargetValue : 100,
        Visualization : #Progress,
    },
    UI.DataPoint #rating : {
        $Type : 'UI.DataPointType',
        Value : last_measure,
        Title : 'last_measure',
        TargetValue : 5,
        Visualization : #Rating,
    },
    UI.DataPoint #last_measure1 : {
        Value : last_measure,
        MinimumValue : 0,
        MaximumValue : 20,
    },
    UI.Chart #last_measure : {
        ChartType : #Bullet,
        Title : '{i18n>last_measure}',
        Measures : [
            last_measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#last_measure1',
                Role : #Axis1,
                Measure : last_measure,
            },
        ],
    },
    UI.DataPoint #last_measure2 : {
        Value : last_measure,
        MaximumValue : last_measure,
    },
    UI.Chart #last_measure1 : {
        ChartType : #Pie,
        Title : 'ChartDefinition Harvey',
        Measures : [
            last_measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#last_measure2',
                Role : #Axis1,
                Measure : last_measure,
            },
        ],
    },
    UI.DataPoint #last_measure3 : {
        Value : last_measure,
        TargetValue : last_measure,
    },
    UI.Chart #last_measure2 : {
        ChartType : #Donut,
        Title : 'ChartDefinition Radial',
        Measures : [
            last_measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#last_measure3',
                Role : #Axis1,
                Measure : last_measure,
            },
        ],
    },
);

annotate service.Measure_Patient with @(
    UI.LineItem #i18nMeasures : [
        {
            $Type : 'UI.DataField',
            Value : created_at,
        },
        {
            $Type : 'UI.DataField',
            Value : measure,            
        },
    ],
    UI.DataPoint #measure : {
        $Type : 'UI.DataPointType',
        Value : measure,
        Title : 'measure',
    },
    UI.HeaderFacets : [
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'measure',
            Target : '@UI.DataPoint#measure',
        },
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'measure1',
            Target : '@UI.DataPoint#progress',
        },
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'measure2',
            Target : '@UI.DataPoint#rating',
        },
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'measure3',
            Target : '@UI.Chart#measure',
        },
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'measure4',
            Target : '@UI.Chart#measure1',
        },
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'measure5',
            Target : '@UI.Chart#measure2',
        },
    ],
    UI.DataPoint #progress : {
        $Type : 'UI.DataPointType',
        Value : measure,
        Title : 'measure',
        TargetValue : 100,
        Visualization : #Progress,
    },
    UI.DataPoint #rating : {
        $Type : 'UI.DataPointType',
        Value : measure,
        Title : 'measure',
        TargetValue : 5,
        Visualization : #Rating,
    },
    UI.DataPoint #measure1 : {
        Value : measure,
        MinimumValue : 0,
        MaximumValue : 20,
    },
    UI.Chart #measure : {
        ChartType : #Bullet,
        Title : '{i18n>measure}',
        Measures : [
            measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#measure1',
                Role : #Axis1,
                Measure : measure,
            },
        ],
    },
    UI.DataPoint #measure2 : {
        Value : measure,
        MaximumValue : measure,
    },
    UI.Chart #measure1 : {
        ChartType : #Pie,
        Title : '{i18n>measure}',
        Measures : [
            measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#measure2',
                Role : #Axis1,
                Measure : measure,
            },
        ],
    },
    UI.DataPoint #measure3 : {
        Value : measure,
        TargetValue : measure,
    },
    UI.Chart #measure2 : {
        ChartType : #Donut,
        Title : '{i18n>measure}',
        Measures : [
            measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#measure3',
                Role : #Axis1,
                Measure : measure,
            },
        ],
    },
    UI.DataPoint #measure4 : {
        Value : measure,
        TargetValue : measure,
    },
    UI.Chart #measure3 : {
        ChartType : #Area,
        Title : 'ChartDefinition Area',
        Measures : [
            measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#measure4',
                Role : #Axis1,
                Measure : measure,
            },
        ],
        Dimensions : [
            measure,
        ],
    },
    UI.DataPoint #measure5 : {
        Value : measure,
    },
    UI.Chart #measure4 : {
        ChartType : #Column,
        Title : '{i18n>last_measures}',
        Measures : [
            measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#measure5',
                Role : #Axis1,
                Measure : measure,
            },
        ],
        Dimensions : [
            measure,
        ],
    },
    UI.DataPoint #measure6 : {
        Value : measure,
    },
    UI.Chart #measure5 : {
        ChartType : #Bar,
        Title : 'ChartDefinition Comparison',
        Measures : [
            measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#measure6',
                Role : #Axis1,
                Measure : measure,
            },
        ],
        Dimensions : [
            measure,
        ],
    },
    UI.DataPoint #measure7 : {
        Value : measure,
    },
    UI.Chart #measure6 : {
        ChartType : #Line,
        Title : 'ChartDefinition Line',
        Measures : [
            measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#measure7',
                Role : #Axis1,
                Measure : measure,
            },
        ],
        Dimensions : [
            measure,
        ],
    },
    UI.DataPoint #measure8 : {
        Value : measure,
    },
    UI.Chart #measure7 : {
        ChartType : #BarStacked,
        Title : 'ChartDefinition Stacked Bar',
        Measures : [
            measure,
        ],
        MeasureAttributes : [
            {
                DataPoint : '@UI.DataPoint#measure8',
                Role : #Axis1,
                Measure : measure,
            },
        ],
    },
    UI.PresentationVariant #measure7 : {
        $Type : 'UI.PresentationVariantType',
        Visualizations : [
            '@UI.Chart#measure7',
        ],
    },
    UI.PresentationVariant #measure6 : {
        $Type : 'UI.PresentationVariantType',
        Visualizations : [
            '@UI.Chart#measure6',
        ],
        SortOrder : [
            {
                $Type : 'Common.SortOrderType',
                Property : measure,
                Descending : false,
            },
        ],
    },
    UI.PresentationVariant #measure61 : {
        $Type : 'UI.PresentationVariantType',
        Visualizations : [
            '@UI.Chart#measure6',
        ],
        SortOrder : [
            {
                $Type : 'Common.SortOrderType',
                Property : measure,
                Descending : false,
            },
        ],
    },
);

