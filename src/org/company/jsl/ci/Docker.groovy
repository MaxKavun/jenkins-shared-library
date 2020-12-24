package org.company.jsl.ci

import org.company.jsl.DefaultSettings

class Docker implements Serializable {
    
    def config
    def dockerImageId
    def job
    def dockerRegistry = DefaultSettings.DOCKER_REGISTRY

    Docker(job, config) {
        this.job = job
        this.config = config
        this.dockerRegistry = this.config["dockerRegistry"] ?: this.dockerRegistry
    }

    def build() {
        job.docker.withRegistry("http://${this.dockerRegistry}", "nexus") {
            this.dockerImageId = job.docker.build("${this.dockerRegistry}/myimage:1.0").id
        }
    }

    def publish() {
        job.docker.withRegistry("http://${this.dockerRegistry}", "nexus") {
            def image = job.docker.image(this.dockerImageId)
            image.push()
            image.push("latest")
        }
    }
}