package org.talend.components.common.oauth;

/**
 * For different platform they will have different way to display the authorize url,
 * print the url on console or open a browser with the url
 */
public interface OauthAuthorizeUrlHandler {
    void display(String url);
}
