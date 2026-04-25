pipeline {
    agent any

    environment {
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
                    echo "===== DEBUG HOST ====="
                    pwd
                    ls -la
                    echo "WORKSPACE = $WORKSPACE"
                '''
            }
        }

        stage('Run tests (Docker Maven)') {
            steps {
                sh '''
                    set +e

                    echo "===== RUN TESTS ====="

                    docker run --rm \
                      -v $HOST_WORKSPACE:/workspace \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test

                    echo "TESTS FINISHED (ignore exit code for CI)"
                '''
            }
        }

        stage('Check Allure results') {
            steps {
                sh '''
                    echo "===== ALLURE RESULTS ====="
                    ls -la allure-results || echo "NO ALLURE RESULTS"
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
                    results: [[path: 'allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "PIPELINE FINISHED"
        }

        success {
            echo "STATUS: SUCCESS"
        }

        failure {
            echo "STATUS: FAILED (tests likely failed)"
        }
    }
}