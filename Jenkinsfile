pipeline {
    agent any

    stages {

        stage('Pull image') {
            steps {
                sh '''
                    docker pull 89.124.113.71:5005/api-tests:1.0
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    docker run --rm \
                      -v /root/jenkins_home/workspace/api_tests:/workspace \
                      -w /workspace \
                      89.124.113.71:5005/api-tests:1.0
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