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
                    docker run --rm \
                      -v /root/jenkins_home/workspace/api_tests:/workspace \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test
                '''
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh '''
                    docker run --rm \
                      -v /root/jenkins_home/workspace/api_tests:/workspace \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn allure:report || true
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