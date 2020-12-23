package org.company.jsl

import org.company.jsl.ci.Maven
import org.company.jsl.ci.Docker

class BuildTool {
    BuildTool(String builder, Map config) {
        this.builder = builder
        this.config = config

        switch (buildTool.toLowerCase()) {
            case 'maven':
                def maven = new Maven(this.config)
                return maven
            case 'docker':
                def docker = new Docker(this.config)
                return docker
        }
    }
}