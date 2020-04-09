#!/usr/bin/env groovy

def writeChangesToFile(content) {
    writeFile file: "Jenkinsfile", text: content
}

@NonCPS
def addBuildDiscardOption(jenkinsFile) {
    def buildDiscarderOption = "buildDiscarder(logRotator(numToKeepStr: '999'))"
    def optionsDirective = 
    '''options {
        buildDiscarder(logRotator(numToKeepStr: '999'))
    }
    stages'''
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
            def newJenkinsFile = jenkinsFile.replaceFirst(pattern) { body,number -> body + buildDiscarderOption + number }
            return newJenkinsFile
            //echo "Add buildDiscard if directive exists without option"
        }
    }
    return null
}

def createNewReleaseBranch() {
    def jenkinsFile = readFile "Jenkinsfile"
    def newJenkinsFile = addBuildDiscardOption(jenkinsFile)
    sh "git branch release/${BUILD_NUMBER}"
    sh "git checkout release/${BUILD_NUMBER}"
    if(newJenkinsFile){
        writeChangesToFile(newJenkinsFile)
        sh 'git add .'
        sh "git commit -m 'Add options directive'"
    }
    sshagent (['22aebe55-e3cf-48af-b4cc-0ca480a4fc77']) {
        sh "git push origin release/${BUILD_NUMBER}"
    }
}

def call() {
    println "Calling new method"
    createNewReleaseBranch()
}