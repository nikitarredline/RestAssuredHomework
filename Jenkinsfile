pipeline {
    agent any

    environment {
        PROJECT_NAME = "api_tests"

        HOST_WORKSPACE = "/root/jenkins_home/workspace/api_tests"
    }

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
                    echo "===== HOST DEBUG ====="
                    pwd
                    ls -la
                    echo "WORKSPACE (Jenkins): $WORKSPACE"
                '''
            }
        }

        stage('Run tests (Docker Maven)') {
            steps {
                sh '''
                    set -e

                    echo "===== RUN TESTS ====="
                    echo "HOST WORKSPACE = $HOST_WORKSPACE"

                    docker run --rm \
                      -v $HOST_WORKSPACE:/workspace \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test
                '''
            }
        }

        stage('Allure Results Check') {
            steps {
                sh '''
                    echo "===== ALLURE RESULTS CHECK ====="
                    ls -la allure-results || echo "NO ALLURE RESULTS FOLDER"
                '''
            }
        }

        stage('Allure') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "PIPELINE FINISHED"
        }

        failure {
            echo "PIPELINE FAILED"
        }
    }
}