#!/usr/bin/env groovy

def createNewReleaseBranch() {
    sh 'ls -al'
    sh 'git branch'
}

def call() {
    println "Calling new method"
    createNewReleaseBranch()
}