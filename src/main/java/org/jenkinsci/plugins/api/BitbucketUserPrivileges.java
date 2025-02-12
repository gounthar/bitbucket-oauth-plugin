package org.jenkinsci.plugins.api;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class BitbucketUserPrivileges {

    @SerializedName("teams")
    Map<String, String> teams;

    public Map<String, String> getTeams() {
        return teams;
    }

    public void setTeams(Map<String, String> teams) {
        this.teams = teams;
    }
}
