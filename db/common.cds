aspect Auditable {
    @readonly created_at  : Timestamp   @title: '{i18n>createdAt}';
    @readonly created_by  : String(255) @title: '{i18n>createdBy}';
    @readonly modified_at : Timestamp   @title: '{i18n>modifiedAt}';
    @readonly modified_by : String(255) @title: '{i18n>modifiedBy}';
}