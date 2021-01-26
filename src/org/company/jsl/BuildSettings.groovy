import jenkins.model.Jenkins

class BuildSettings {
    private Stringa branchName = null
    private Boolean versionedSemantically = false
    private List<String> upstreamDependencies = []

    public Boolean getVersionedSemantically() {
        return versionedSemantically
    }
}
