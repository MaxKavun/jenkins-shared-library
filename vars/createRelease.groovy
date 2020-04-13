#!/usr/bin/env groovy


def call() {
    /*
      * Create release code with creating new branch and pushing to the SCM
      * Just for testing
    */
    if (BuildSettings.versionedSemantically) {
        sh "git branch release/${BUILD_NUMBER}"
        sh "git checkout release/${BUILD_NUMBER}"
        sshagent (['22aebe55-e3cf-48af-b4cc-0ca480a4fc77']) {
            sh "git push origin release/${BUILD_NUMBER}"
        }
    }
}