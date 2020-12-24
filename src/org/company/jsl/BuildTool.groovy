package org.company.jsl

import org.company.jsl.ci.Maven
import org.company.jsl.ci.Docker

class BuildTool implements Serializable {

    def builder
    def config
    def tool
    def job

    BuildTool(builder, job) {
        
        this.builder = builder
        this.config = "profiles"
        this.job = job

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