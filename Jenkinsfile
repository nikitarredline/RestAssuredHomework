pipeline {
    agent any

    environment {
        // важно: фиксируем workspace Jenkins (НЕ придумываем root)
        WS = "${WORKSPACE}"
    }

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Debug host workspace') {
            steps {
                sh '''
                    echo "===== HOST DEBUG ====="
                    echo "WORKSPACE=$WORKSPACE"
                    pwd
                    ls -la
                    echo "pom.xml check:"
                    test -f pom.xml && echo "POM EXISTS" || echo "POM MISSING"
                '''
            }
        }

        stage('Debug Docker mount') {
            steps {
                sh '''
                    echo "===== DOCKER DEBUG ====="

                    docker run --rm \
                        -v "$WORKSPACE:$WORKSPACE" \
                        -w "$WORKSPACE" \
                        alpine sh -c "
                            echo INSIDE DOCKER;
                            pwd;
                            ls -la;
                            echo 'POM check:';
                            test -f pom.xml && echo FOUND || echo MISSING
                        "
                '''
            }
        }

        stage('Run tests (FIXED)') {
            steps {
                sh '''
                    echo "===== MAVEN RUN ====="

                    docker run --rm \
                        -v "$WORKSPACE:$WORKSPACE" \
                        -w "$WORKSPACE" \
                        maven:3.9.9-eclipse-temurin-21 \
                        mvn -e clean test
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