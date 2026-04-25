pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    set +e

                    docker run --rm \
                      -v /root/jenkins_home/workspace/api_tests:/workspace \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test

                    echo "TESTS FINISHED (ignore exit code for reporting)"
                '''
            }
        }
    }

    post {
        always {
            echo "GENERATING ALLURE REPORT"

            allure([
                includeProperties: false,
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            echo "PIPELINE FINISHED"
        }
    }
}