package org.company.jsl.ci

import org.company.jsl.DefaultSettings

class Docker implements Serializable {
    
    def config
    def dockerImageId
    def job
    def dockerRegistry = DefaultSettings.DOCKER_REGISTRY
    def dockerCredentials = DefaultSettings.DOCKER_CREDENTIALS
    def imageName
    def imageVersion

    Docker(job, config) {
        try {
            this.job = job
            this.config = config
            this.dockerRegistry = this.config["dockerRegistry"] ?: this.dockerRegistry
            this.imageName = this.config["imageName"]
            this.imageVersion = this.config["imageVersion"]
            if (this.imageVersion) {
                throw new Exception('Required arguments are missed')
            }
        } catch(Exception ex) {
            job.println(ex.getMessage())
            job.println(DefaultSettings.DOCKER_PARAMS_DESC)
            job.error("Fail job")
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
            image.push("latest")
        }
    }
}