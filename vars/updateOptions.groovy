#!/usr/bin/env groovy

def getNumLogsForRotation() {
    //def currentBranch = sh(script: "git branch --show-current", returnStdout: true).trim()
    def currentBranch = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
    print currentBranch
    def matcher = currentBranch =~ /(release.+|hotfix.*)/
    def numLogsForRotation = matcher ? 999 : 10
    return numLogsForRotation 
}

@NonCPS
def addBuildDiscardOption(jenkinsFile,numLogsForRotation) {
    def buildDiscarderOption = "buildDiscarder(logRotator(numToKeepStr: '${numLogsForRotation}'))"
    def optionsDirective = 
    """options {
        buildDiscarder(logRotator(numToKeepStr: '${numLogsForRotation}'))
    }
    stages"""
    def pattern = /(options.*[\\{][^}]*)([?<input>\\}])/
    def matcher = jenkinsFile =~ pattern
    if (!matcher) {
        def newJenkinsFile = jenkinsFile.replace('stages',optionsDirective)
        return newJenkinsFile
    } else {
        matcher = jenkinsFile =~ /buildDiscarder\(logRotator.+/
        if (matcher){
            def newJenkinsFile = jenkinsFile.replaceFirst(/buildDiscarder\(logRotator.+/) { buildDiscarderOption }
            return newJenkinsFile
        } else {
            def newJenkinsFile = jenkinsFile.replaceFirst(pattern) { match, firstPart, closeBracket-> "${firstPart}\t${buildDiscarderOption}\n\t${closeBracket}"}
            return newJenkinsFile
        }
    }
    return null
}

def createNewReleaseBranch() {
    def jenkinsFile = readFile "Jenkinsfile"
    def numLogsForRotation = getNumLogsForRotation()
    def newJenkinsFile = addBuildDiscardOption(jenkinsFile,numLogsForRotation)
    sh "git branch release/${BUILD_NUMBER}"
    sh "git checkout release/${BUILD_NUMBER}"
    if(newJenkinsFile){
        writeFile file: "Jenkinsfile", text: newJenkinsFile
        sh 'git add .'
        sh "git commit -m 'Add options directive'"
    }
    sshagent (['22aebe55-e3cf-48af-b4cc-0ca480a4fc77']) {
        sh "git push origin release/${BUILD_NUMBER}"
    }
}

def call() {
    createNewReleaseBranch()
}