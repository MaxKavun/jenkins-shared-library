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
    }
}
