package org.company.jsl.ci

class Docker {
    Docker(config) {
        this.config = config
    }

    def build() {
        docker.withRegistry("https://nexus.com:5000", "hub_rw") {
            this.dockerImageId = docker.build("nexus.com:5000/artifact:1.0").id
        }
    }

    def publish() {
        docker.withRegistry("https://nexus.com:5000", "hub_rw") {
            def image = docker.image(this.dockerImageId)
            image.push()
            image.push("latest")
        }
    }
}