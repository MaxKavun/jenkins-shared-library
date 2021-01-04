package org.company.jsl.ci

class Maven implements Serializable {

    def config
    def job

    Maven(job,config) {
        this.config = config
        this.job = job
    }
    def tool = "Maven"
}