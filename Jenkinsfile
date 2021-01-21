pipeline {
    agent any
    
    tools {
        maven 'mvn'
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
                    def scannerHome = tool 'SonarScanner 4.6';
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
    }
}
