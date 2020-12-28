package org.company.jsl

import org.company.jsl.ci.Maven
import org.company.jsl.ci.Docker
import hudson.model.*

class BuildTool implements Serializable {

    def builder
    def config
    def tool
    def job

    BuildTool(Map parameters) {

        this.builder = parameters['builder']
        this.job = parameters['job']
        this.config = parameters

        def myJob = Thread.currentThread().executable

        this.job.println(myJob)
        this.job.println(this.job)

        switch (this.builder.toLowerCase()) {
            case 'maven':
                def maven = new Maven(this.job, this.config)
                this.tool = maven
                break
            case 'docker':
                def docker = new Docker(this.job, this.config)
                this.tool = docker
                break
        }
    }

    def returnBuildTool() {
        return this.tool
    }
}