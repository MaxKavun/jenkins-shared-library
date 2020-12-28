package org.company.jsl

import org.company.jsl.ci.Maven
import org.company.jsl.ci.Docker

def tool

def builder = parameters['builder']
def job = parameters['job']
def config = parameters

switch (builder.toLowerCase()) {
    case 'maven':
        tool = new Maven(job, config)
        break
    case 'docker':
        tool = new Docker(job, config)
        break
}

return tool