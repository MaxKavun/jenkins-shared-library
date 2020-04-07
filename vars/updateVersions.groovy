#!/usr/bin/env groovy

def createNewReleaseBranch() {
    sh "git branch release/${BUILD_NUMBER}"
    sh "git checkout release/${BUILD_NUMBER}"
    sh "git push origin release/${BUILD_NUMBER}"
}

def call() {
    println "Calling new method"
    createNewReleaseBranch()
}