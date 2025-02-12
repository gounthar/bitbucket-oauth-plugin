package org.jenkinsci.plugins;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.plugins.api.BitbucketApiService;
import org.jenkinsci.plugins.api.BitbucketUser;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.scribe.model.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.providers.AbstractAuthenticationToken;

public class BitbucketAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -7826610577724673531L;

    private Token accessToken;
    private BitbucketUser bitbucketUser;

    public BitbucketAuthenticationToken(Token accessToken, String apiKey, String apiSecret) {
        this.accessToken = accessToken;
        this.bitbucketUser = new BitbucketApiService(apiKey, apiSecret).getUserByToken(accessToken);

        boolean authenticated = false;

        if (bitbucketUser != null) {
            authenticated = true;
        }

        setAuthenticated(authenticated);
    }

    @Override
    public GrantedAuthority[] getAuthorities() {
        return this.bitbucketUser != null ? this.bitbucketUser.getAuthorities() : new GrantedAuthority[0];
    }

    /**
     * @return the accessToken
     */
    public Token getAccessToken() {
        return accessToken;
    }

    @Override
    public Object getCredentials() {
        return StringUtils.EMPTY;
    }

    @Override
    public Object getPrincipal() {
        return getName();
    }

    @Override
    public String getName() {
        return (bitbucketUser != null ? bitbucketUser.getUsername() : null);
    }

    @Restricted(NoExternalUse.class)
    public BitbucketUser getBitbucketUser() {
        return bitbucketUser;
    }
}
