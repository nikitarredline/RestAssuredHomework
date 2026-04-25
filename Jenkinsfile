pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Debug workspace') {
            steps {
                sh '''
                    echo "WORKSPACE=$WORKSPACE"
                    pwd
                    ls -la
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    docker run --rm \
                      -v $WORKSPACE:/workspace \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test

                    ls -la target/allure-results || true
                '''
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "PIPELINE FINISHED"
        }
        failure {
            echo "STATUS: FAILED"
        }
    }
}