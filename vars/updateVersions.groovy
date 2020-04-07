#!/usr/bin/env groovy

def createNewReleaseBranch() {
    sh 'ls -l'
    sh 'git branch'
}

def call() {
    println "Calling new method"
    createNewReleaseBranch()
}