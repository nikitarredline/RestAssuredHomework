pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Debug workspace') {
            steps {
                sh '''
                    echo WORKSPACE=$WORKSPACE
                    pwd
                    ls -la
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    echo RUN TESTS VIA DOCKER

                    docker run --rm \
                      -v $WORKSPACE:/workspace \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test
                '''
            }
        }

        stage('Allure Report') {
            steps {
                echo "Allure stage"
            }
        }
    }

    post {
        always {
            echo "PIPELINE FINISHED"
        }
    }
}