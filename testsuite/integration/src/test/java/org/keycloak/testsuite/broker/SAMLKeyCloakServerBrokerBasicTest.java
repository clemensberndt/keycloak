package org.keycloak.testsuite.broker;

import org.junit.ClassRule;
import org.junit.Test;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.testsuite.rule.AbstractKeycloakRule;
import org.keycloak.testsuite.KeycloakServer;
import org.keycloak.saml.processing.api.saml.v2.request.SAML2Request;
import org.keycloak.dom.saml.v2.protocol.ResponseType;
import org.keycloak.saml.processing.web.util.PostBindingUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author pedroigor
 */
public class SAMLKeyCloakServerBrokerBasicTest extends AbstractKeycloakIdentityProviderTest {

    @ClassRule
    public static AbstractKeycloakRule samlServerRule = new AbstractKeycloakRule() {

        @Override
        protected void configureServer(KeycloakServer server) {
            server.getConfig().setPort(8082);
        }

        @Override
        protected void configure(KeycloakSession session, RealmManager manager, RealmModel adminRealm) {
            server.importRealm(getClass().getResourceAsStream("/broker-test/test-broker-realm-with-saml.json"));
        }

        @Override
        protected String[] getTestRealms() {
            return new String[] { "realm-with-saml-idp-basic" };
        }
    };

    @Override
    protected String getProviderId() {
        return "kc-saml-idp-basic";
    }

    @Override
    protected void doAssertFederatedUser(UserModel federatedUser, IdentityProviderModel identityProviderModel, String expectedEmail, boolean isProfileUpdateExpected) {
        if (isProfileUpdateExpected) {
            super.doAssertFederatedUser(federatedUser, identityProviderModel, expectedEmail, isProfileUpdateExpected);
        } else {
            if (expectedEmail == null) {
                // Need to handle differences for various databases (like Oracle)
                assertTrue(federatedUser.getEmail() == null || federatedUser.getEmail().equals(""));
            } else {
                assertEquals(expectedEmail, federatedUser.getEmail());
            }
            assertNull(federatedUser.getFirstName());
            assertNull(federatedUser.getLastName());
        }
    }

    @Override
    protected void doAssertFederatedUserNoEmail(UserModel federatedUser) {
        assertEquals("kc-saml-idp-basic.test-user-noemail", federatedUser.getUsername());
        //assertEquals("", federatedUser.getEmail());
        assertEquals(null, federatedUser.getFirstName());
        assertEquals(null, federatedUser.getLastName());
    }

    @Override
    protected void doAssertTokenRetrieval(String pageSource) {
        try {
            SAML2Request saml2Request = new SAML2Request();
            ResponseType responseType = (ResponseType) saml2Request
                    .getSAML2ObjectFromStream(PostBindingUtil.base64DecodeAsStream(pageSource));
                    //.getSAML2ObjectFromStream(PostBindingUtil.base64DecodeAsStream(URLDecoder.decode(pageSource, "UTF-8")));

            assertNotNull(responseType);
            assertFalse(responseType.getAssertions().isEmpty());
        } catch (Exception e) {
            fail("Could not parse token.");
        }
    }

    @Override
    @Test
    public void testSuccessfulAuthenticationWithoutUpdateProfile() {
        super.testSuccessfulAuthenticationWithoutUpdateProfile();
    }

    @Override
    @Test
    public void testSuccessfulAuthentication() {
        super.testSuccessfulAuthentication();
    }

    @Test
    public void testAccountManagementLinkIdentity() {
        super.testAccountManagementLinkIdentity();
    }

    @Test
    public void testTokenStorageAndRetrievalByApplication() {
        super.testTokenStorageAndRetrievalByApplication();
    }

    @Test
    public void testSuccessfulAuthenticationWithoutUpdateProfile_newUser_emailAsUsername() {
        super.testSuccessfulAuthenticationWithoutUpdateProfile_newUser_emailAsUsername();
    }

    @Test
    public void testSuccessfulAuthenticationUpdateProfileOnMissing_nothingMissing() {
        // skip this test as this provider do not return name and surname so something is missing always
    }
}
