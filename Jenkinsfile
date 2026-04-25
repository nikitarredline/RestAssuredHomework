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
                    echo "===== HOST ====="
                    pwd
                    ls -la
                    test -f pom.xml && echo "POM EXISTS" || echo "POM MISSING"
                '''
            }
        }

        stage('Run tests (FIXED MOUNT)') {
            steps {
                sh '''
                    echo "===== DOCKER RUN ====="

                    docker run --rm \
                        -v $(pwd):/workspace \
                        -w /workspace \
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