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
                    echo "WORKSPACE=$WORKSPACE"
                    pwd
                    ls -la
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    echo "WORKSPACE=$WORKSPACE"
                    HOST_WS=$(readlink -f $WORKSPACE)
                    echo "HOST_WORKSPACE=$HOST_WS"

                    docker run --rm \
                      -v "$HOST_WS:/app" \
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