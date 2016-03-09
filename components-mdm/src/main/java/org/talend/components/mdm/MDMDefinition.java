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
package org.talend.components.mdm;

import java.io.InputStream;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.ComponentImageType;

public abstract class MDMDefinition extends AbstractComponentDefinition {

    protected String componentName;

    public MDMDefinition(String componentName) {
        this.componentName = componentName;
    }

    @Override
    public InputStream getMavenPom() {
        return this.getClass().getResourceAsStream("/org/talend/components/mdm/pom.xml"); //$NON-NLS-1$
    }

    @Override
    public String[] getFamilies() {
        return new String[] { "Talend MDM" }; //$NON-NLS-1$
    }

    @Override
    public String getPngImagePath(ComponentImageType imageType) {
        switch (imageType) {
        case PALLETE_ICON_32X32:
            return componentName + "_icon32.png"; //$NON-NLS-1$
        }
        return null;
    }

    @Override
    public String getName() {
        return componentName;
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
}
