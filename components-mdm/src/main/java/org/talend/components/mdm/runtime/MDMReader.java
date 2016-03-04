package org.talend.components.mdm.runtime;

//============================================================================
//
//Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
//This source code is available under agreement available at
//%InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
//You should have received a copy of the agreement
//along with this program; if not, write to Talend SA
//9 rue Pages 92150 Suresnes, France
//
//============================================================================
import java.io.IOException;
import java.util.NoSuchElementException;

import org.joda.time.Instant;
import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.component.runtime.BoundedSource;

public class MDMReader implements BoundedReader {

    @Override
    public boolean start() throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean advance() throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getCurrent() throws NoSuchElementException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public Double getFractionConsumed() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BoundedSource getCurrentSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BoundedSource splitAtFraction(double fraction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Instant getCurrentTimestamp() throws NoSuchElementException {
        // TODO Auto-generated method stub
        return null;
    }

}
