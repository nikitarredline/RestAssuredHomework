pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Debug') {
            steps {
                sh '''
                    echo WORKSPACE=$WORKSPACE
                    ls -la
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    echo RUN TESTS VIA DOCKER

                    docker run --rm \
                      -v $WORKSPACE:/root \
                      -w /root \
                      maven:3.9.9-eclipse-temurin-21 \
                      bash -c "mvn clean test"
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