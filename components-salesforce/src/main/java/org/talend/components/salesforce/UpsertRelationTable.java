package org.talend.components.salesforce;

import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.Property;

import static org.talend.daikon.properties.PropertyFactory.newEnum;
import static org.talend.daikon.properties.PropertyFactory.newString;

public class UpsertRelationTable extends ComponentProperties{

    /**
     * named constructor to be used is these properties are nested in other properties. Do not subclass this method for
     * initialization, use {@link #init()} instead.
     *
     * @param name
     */
    public UpsertRelationTable(String name) {
        super(name);
    }

    public Property columnName = newEnum("columnName");
    public Property lookupFieldName = newString("lookupFieldName");
    public Property lookupFieldModuleName = newString("lookupFieldModuleName");
    public Property lookupFieldExternalIdName = newString("lookupFieldExternalIdName");

}
