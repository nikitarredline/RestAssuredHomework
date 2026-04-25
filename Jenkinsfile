pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Build Docker image') {
            steps {
                sh '''
                    docker build -t api-tests .
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    docker run --rm api-tests
                '''
            }
        }
    }

    post {
        always {
            echo "PIPELINE FINISHED"

            allure([
                includeProperties: false,
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
        }
    }
}