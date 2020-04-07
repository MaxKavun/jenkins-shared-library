#!/usr/bin/env groovy

def writeChangesToFile(content) {
    writeFile file: "Jenkinsfile", text: content
}

@NonCPS
def addBuildDiscardOption(jenkinsFile) {
    def optionsDirective = 
    '''options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages'''
    def matcher = jenkinsFile =~ /options.*[\\{]([^}]*)[\\}]/
    if (!matcher) {
        def newJenkinsFile = jenkinsFile.replace('stages',optionsDirective)
        print newJenkinsFile
        return newJenkinsFile
    }
    print matcher.size()
}

def createNewReleaseBranch() {
    def jenkinsFile = readFile "Jenkinsfile"
    def newJenkinsFile = addBuildDiscardOption(jenkinsFile)
    if(newJenkinsFile)
        writeChangesToFile(newJenkinsFile)
    sh "git branch release/${BUILD_NUMBER}"
    sh "git checkout release/${BUILD_NUMBER}"
    sshagent (['22aebe55-e3cf-48af-b4cc-0ca480a4fc77']) {
        sh "git push origin release/${BUILD_NUMBER}"
    }
}

def call() {
    println "Calling new method"
    createNewReleaseBranch()
}