package org.company.jsl

import org.company.jsl.ci.Maven
import org.company.jsl.ci.Docker

class BuildTool {

    def builder
    def config
    BuildTool() {
        this.builder = "Docker"
        this.config = "profiles"

        switch (this.builder.toLowerCase()) {
            case 'maven':
                def maven = new Maven(this.config)
                this.tool = maven
                break
            case 'docker':
                def docker = new Docker(this.config)
                this.tool = docker
                break
        }
    }

    def returnBuildTool() {
        return this.tool
    }
}