package org.company.jsl;

import jenkins.model.Jenkins

class BuildSettings {
    private String branchName = null
    private Boolean versionedSemantically = false
    private List<String> upstreamDependencies = []

    public Boolean getVersionedSemantically() {
        return versionedSemantically
    }
}
