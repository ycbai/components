// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.api.properties.fullexample;

import org.talend.components.api.exception.ComponentException;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.Property;
import org.talend.daikon.properties.PropertyFactory;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;

/**
 * This component Properties is designed for the client to test all the possible interactions
 * with the component service.
 */
public class ComponentPropertiesFullExample extends ComponentProperties {

    public ComponentPropertiesFullExample(String name) {
        super(name);
    }

    public final Property stringProp = PropertyFactory.newString("stringProp", "initialValue");

    /** this shall hide stringProp widget according to it value. */
    public final Property hideStringPropProp = PropertyFactory.newBoolean("hideStringPropProp", false);

    public ValidationResult validateStringProp() {
        return ValidationResult.OK.setMessage(stringProp.getStringValue());
    }

    public ValidationResult beforeStringProp() {
        return ValidationResult.OK.setMessage(stringProp.getStringValue());
    }

    public ValidationResult beforeStringPropPresent() {
        return ValidationResult.OK.setMessage(stringProp.getStringValue());
    }

    public ValidationResult afterStringProp() {
        return ValidationResult.OK.setMessage(stringProp.getStringValue());
    }

    public void afterHideStringPropProp() {
        refreshLayout(getForm(Form.MAIN));
    }

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(stringProp);
    }

    @Override
    public void refreshLayout(Form form) {
        super.refreshLayout(form);
        switch (form.getName()) {
        case Form.MAIN:
            form.getWidget(stringProp.getName()).setHidden(hideStringPropProp.getBooleanValue());
            break;

        default:
            ComponentException.unexpectedException("Unknow form, shoud never happend");
            break;
        }
    }
}
