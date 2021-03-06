package org.keycloak.representations.idm;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class UserFederationMapperTypeRepresentation {
    protected String id;
    protected String name;
    protected String category;
    protected String helpText;

    protected UserFederationMapperSyncConfigRepresentation syncConfig;

    protected List<ConfigPropertyRepresentation> properties  = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public UserFederationMapperSyncConfigRepresentation getSyncConfig() {
        return syncConfig;
    }

    public void setSyncConfig(UserFederationMapperSyncConfigRepresentation syncConfig) {
        this.syncConfig = syncConfig;
    }

    public List<ConfigPropertyRepresentation> getProperties() {
        return properties;
    }

    public void setProperties(List<ConfigPropertyRepresentation> properties) {
        this.properties = properties;
    }
}
