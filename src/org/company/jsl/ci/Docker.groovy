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
        job.docker.withRegistry("http://172.17.0.1:5050", "nexus") {
            this.dockerImageId = docker.build("172.17.0.1:5050/myimage:1.0").id
        }
    }

    def publish() {
        job.docker.withRegistry("http://172.17.0.1:5050", "nexus") {
            def image = docker.image(this.dockerImageId)
            image.push()
            image.push("latest")
        }
    }
}