package org.company.jsl.ci

import org.company.jsl.DefaultSettings

/**
 * A Docker class
 * Consist of methods which need to be executed as part of Jenkinsfile
 * Can be expanded
 */
class Docker implements Serializable {
    
    def config
    def dockerImageId
    def job
    def dockerRegistry = DefaultSettings.DOCKER_REGISTRY
    def dockerCredentials = DefaultSettings.DOCKER_CREDENTIALS
    def imageName
    def imageVersion

    /**
     * Init method
     * @param job the link to the Jenkins job
     * @param config arguments which were provided in Jenkinsfile
     */
    Docker(job, config) {
        try {
            this.job = job
            this.config = config
            this.dockerRegistry = this.config["dockerRegistry"] ?: this.dockerRegistry
            this.imageName = this.config["imageName"]
            this.imageVersion = this.config["imageVersion"]
            if (this.imageVersion == null || this.imageName == null) {
                throw new Exception('Required arguments are missed')
            }
        } catch(Exception ex) {
            println(ex.getMessage())
            job.error(DefaultSettings.DOCKER_PARAMS_DESC)
        }
    }

    def build() {
        job.docker.withRegistry("http://${this.dockerRegistry}", this.dockerCredentials) {
            this.dockerImageId = job.docker.build("${this.dockerRegistry}/${this.imageName}:${this.imageVersion}").id
        }
    }

    def publish() {
        job.docker.withRegistry("http://${this.dockerRegistry}", this.dockerCredentials) {
            def image = job.docker.image(this.dockerImageId)
            image.push()
        }
    }

    def removeImages() {
        job.sh(script: "docker images | grep ${this.imageName} | awk '{system(\"docker rmi -f \" \$1 \":\" \$2)}'")
    }
}