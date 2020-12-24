package org.company.jsl

class DefaultSettings {
    public static final String SSH_CREDENTIALS_ID = 'jenkins-deployment'
    public static final String DOCKER_REGISTRY = '172.17.0.1:5050'
    public static final String DOCKER_CREDENTIALS = 'nexus'
    public static final String DOCKER_PARAMS_DESC = """
        
    buildTool = new BuildTool(
        builder        : "Docker",          - [REQUIRED]
        job            : this,              - [REQUIRED]
        dockerRegistry : "172.17.0.1:5050", - [OPTIONAL]
        imageName      : "myimage",         - [REQUIRED]
        imageVersion   : "1.0",             - [REQUIRED]
    ).returnBuildTool()

    """
}