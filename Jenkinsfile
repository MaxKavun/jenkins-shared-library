pipeline {
    agent any
    
    tools {
        maven 'mvn'
        maven 'SonarScanner 4.0'
    }

    stages {
        stage('Maven Build') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Sonarqube scan') {
            steps {
                withSonarQubeEnv('nexus') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
    }
}
