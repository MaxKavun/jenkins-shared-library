package org.company.jsl.ci

class Docker implements Serializable {
    
    def config
    def dockerImageId
    def job

    Docker(job, config) {
        this.job = job
        this.config = config
    }

    def build() {
        job.docker.withRegistry("https://nexus.com:5000", "hub_rw") {
            this.dockerImageId = docker.build("nexus.com:5000/artifact:1.0").id
        }
    }

    def publish() {
        job.docker.withRegistry("https://nexus.com:5000", "hub_rw") {
            def image = docker.image(this.dockerImageId)
            image.push()
            image.push("latest")
        }
    }
}