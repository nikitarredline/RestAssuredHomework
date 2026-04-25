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
                    echo "WORKSPACE:"
                    pwd
                    ls -R
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
                      mvn -f /workspace/**/pom.xml clean test
                '''
            }
        }

        stage('Allure Report') {
            steps {
                sh '''
                    echo "Generate report (if configured)"
                '''
            }
        }
    }

    post {
        always {
            echo 'PIPELINE FINISHED'
        }
    }
}