pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Debug') {
            steps {
                sh '''
                    echo "HOST WORKSPACE:"
                    pwd
                    ls -la
                '''
            }
        }

        stage('Run tests in Docker') {
            steps {
                sh '''
                    WORKDIR=$(pwd)

                    echo "Using WORKDIR=$WORKDIR"

                    docker run --rm \
                      -v "$WORKDIR:$WORKDIR" \
                      -w "$WORKDIR" \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test
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