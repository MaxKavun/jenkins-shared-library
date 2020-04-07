#!/usr/bin/env groovy

def addBuildDiscardOption() {
    def optionsDirective = '''
        options {
            buildDiscarder(logRotator(numToKeepStr: '5'))
        }
    '''
    def jenkinsFile = readFile "Jenkinsfile"
    def matcher = jenkinsFile =~ /options.*\\{([^}]*)\\}/
    assert matcher.find()
    print matcher.size()
}

def createNewReleaseBranch() {
    addBuildDiscardOption()
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