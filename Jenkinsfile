pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Debug workspace') {
            steps {
                sh '''
                    echo "WORKSPACE=$WORKSPACE"
                    pwd
                    ls -la $WORKSPACE
                '''
            }
        }

        stage('Verify mount') {
            steps {
                sh '''
                    docker run --rm \
                      -v "$WORKSPACE:$WORKSPACE" \
                      alpine sh -c "ls -la $WORKSPACE && test -f $WORKSPACE/pom.xml && echo POM_OK || echo POM_MISSING"
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                    echo "Running tests in Docker using Jenkins workspace"

                    docker run --rm \
                      -v "$WORKSPACE:$WORKSPACE" \
                      -w "$WORKSPACE" \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test

                    ls -la $WORKSPACE/target/allure-results || true
                '''
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    if (fileExists('target/allure-results')) {
                        allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'target/allure-results']]
                        ])
                    } else {
                        echo "NO ALLURE RESULTS FOUND"
                    }
                }
            }
        }
    }

    post {
        always {
            echo "PIPELINE FINISHED"
        }
        failure {
            echo "STATUS: FAILED"
        }
    }
}