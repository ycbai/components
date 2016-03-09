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
package org.talend.components.mdm.tmdmrestinput;

import static org.talend.components.mdm.MDMConstants.CONTAINER;
import static org.talend.components.mdm.MDMConstants.QUERY;
import static org.talend.components.mdm.MDMConstants.STRIP_CHARS;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.component.runtime.BoundedSource;
import org.talend.components.api.container.RuntimeContainer;

public class TMDMRestInputReader implements BoundedReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TMDMRestInputReader.class);

    protected TMDMRestInputSource source;

    protected TMDMRestInputProperties properties;

    protected RuntimeContainer container;

    protected String inputResult;

    public TMDMRestInputReader(RuntimeContainer container, TMDMRestInputSource source, TMDMRestInputProperties properties) {
        this.container = container;
        this.source = source;
        this.properties = properties;
    }

    @Override
    public boolean start() throws IOException {
        // Get parameters from properties
        String url = StringUtils.strip(properties.connection.url.getStringValue(), STRIP_CHARS);
        String username = StringUtils.strip(properties.connection.userPassword.userId.getStringValue(), STRIP_CHARS);
        String password = StringUtils.strip(properties.connection.userPassword.password.getStringValue(), STRIP_CHARS);
        String containerName = StringUtils.strip(properties.container.containerName.getStringValue(), STRIP_CHARS);
        String containerType = properties.container.containerType.getStringValue();
        String queryText = StringUtils.strip(properties.queryText.getStringValue(), STRIP_CHARS);

        // Call REST API to get data
        Client client = ClientBuilder.newClient().register(HttpAuthenticationFeature.basic(username, password));
        try {
            Response response = client.target(url).path(containerName).path(QUERY).queryParam(CONTAINER, containerType).request()
                    .accept(MediaType.APPLICATION_XML).put(Entity.json(queryText));
            if (response.getStatus() == 200) {
                inputResult = response.readEntity(String.class);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("tMDMRestInput - Gets data from service: \n" + inputResult); //$NON-NLS-1$
                }
                return true;
            } else {
                LOGGER.error(response.toString());
                throw new RuntimeException(response.toString());
            }
        } finally {
            client.close();
        }
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
