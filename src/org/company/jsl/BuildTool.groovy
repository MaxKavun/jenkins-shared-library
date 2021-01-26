package org.company.jsl

import org.company.jsl.ci.Maven
import org.company.jsl.ci.Docker
import com.cloudbees.groovy.cps.NonCPS

/**
 * A BuildTool class 
 * Another abstraction for listing all of ours 'Build' tools
 * Currently it supports docker and maven
 */
@NonCPS
class BuildTool implements Serializable {

    /** the name of build tool */
    def builder
    /** the configuration of specific tool */
    def config
    /** the variable which reference to the object of class tool */
    def tool
    /** the reference to the jenkins job */
    def job

    /**
     * Init method to instantiate necessary build tool
     *
     * @param parameters the arguments which were provided in Jenkinsfile
     */
    BuildTool(Map parameters) {

        this.builder = parameters['builder']
        this.job = parameters['job']
        this.config = parameters

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

    /**
     * Method returns build tool object
     * @return a object of tool class
     */
    def returnBuildTool() {
        return this.tool
    }
}
