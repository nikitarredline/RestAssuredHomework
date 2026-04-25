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
                    ls -la $WORKSPACE
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    echo "RUN MAVEN IN DOCKER"
                    echo "WORKSPACE=$WORKSPACE"

                    docker run --rm \
                      -v "$WORKSPACE:/workspace" \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test

                    echo "CHECK ALLURE RESULTS"
                    ls -la $WORKSPACE/target/allure-results || true
                '''
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    if (fileExists('target/allure-results')) {
                        allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'target/allure-results']]
                        ])
                    } else {
                        echo "NO ALLURE RESULTS FOUND"
                    }
                }
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