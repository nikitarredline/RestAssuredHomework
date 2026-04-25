pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Debug paths') {
            steps {
                sh '''
                    echo "JENKINS WORKSPACE: $WORKSPACE"
                    pwd
                    ls -la

                    echo "HOST ROOT CHECK:"
                    ls -la /root/jenkins_home/workspace/api_tests || true
                    ls -la /var/jenkins_home/workspace/api_tests || true
                '''
            }
        }

        stage('Run tests (FIXED PATH)') {
            steps {
                sh '''
                    echo "USING HOST PATH"

                    docker run --rm \
                        -v /root/jenkins_home/workspace/api_tests:/workspace \
                        -w /workspace \
                        maven:3.9.9-eclipse-temurin-21 \
                        mvn clean test
                '''
            }
        }

        stage('Check Allure results') {
            steps {
                sh '''
                    ls -la /root/jenkins_home/workspace/api_tests/target/allure-results || true
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
    }
}