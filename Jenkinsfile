pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Debug host workspace') {
            steps {
                sh '''
                    echo "===== JENKINS DEBUG ====="
                    echo "WORKSPACE = $WORKSPACE"
                    pwd
                    ls -la
                    echo "========================="
                '''
            }
        }

        stage('Debug Docker visibility') {
            steps {
                sh '''
                    echo "===== DOCKER MOUNT TEST ====="

                    docker run --rm \
                      -v "$WORKSPACE:/app" \
                      alpine sh -c "echo INSIDE DOCKER; pwd; ls -la /app"

                    echo "============================="
                '''
            }
        }

        stage('Check pom.xml explicitly') {
            steps {
                sh '''
                    echo "===== POM CHECK ====="

                    docker run --rm \
                      -v "$WORKSPACE:/app" \
                      alpine sh -c "test -f /app/pom.xml && echo POM FOUND || echo POM NOT FOUND"

                    echo "====================="
                '''
            }
        }

        stage('Run Maven (only if OK)') {
            steps {
                sh '''
                    echo "===== MAVEN RUN ====="

                    docker run --rm \
                      -v "$WORKSPACE:/app" \
                      -w /app \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test
                '''
            }
        }
    }

    post {
        always {
            echo "PIPELINE FINISHED"
        }
    }
}