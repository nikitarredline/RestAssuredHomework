pipeline {
    agent any

    stages {
        stage('Print real path') {
          steps {
            sh '''
              echo "PWD=$(pwd)"
              readlink -f .
              ls -la
            '''
          }
        }

        stage('Test mount outside Maven') {
          steps {
            sh '''
              HOST_DIR=$(pwd)

              docker run --rm \
                -v "$HOST_DIR:$HOST_DIR" \
                alpine ls -la "$HOST_DIR"
            '''
          }
        }

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

        stage('Run tests') {
            steps {
                sh '''
                    set -e

                    echo "WORKSPACE (Jenkins): $WORKSPACE"
                    echo "HOST PATH used for Docker: /root/jenkins_home/workspace/api_tests"

                    docker run --rm \
                      -v /root/jenkins_home/workspace/api_tests:/workspace \
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