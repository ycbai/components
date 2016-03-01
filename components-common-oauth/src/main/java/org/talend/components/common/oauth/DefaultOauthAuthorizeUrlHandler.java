package org.talend.components.common.oauth;

/**
 * For component runtime, just display the url on console, and let the user paste it into browser
 */
public class DefaultOauthAuthorizeUrlHandler implements OauthAuthorizeUrlHandler {
    @Override
    public void display(String url) {
        System.out.println("Paste this URL into a web browser to authorize Salesforce Access:");
        System.out.println(url);
    }
}
