pipeline {
    agent any
    environment {
        DOCKER_IMAGE_NAME = 'gwrgwr/murilo.ramos'
        DOCKER_CREDENTIALS = 'docker-hub-credentials'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Pré-build: Instalar technova-common') {
            steps {
                git url: 'https://github.com/gwrgwr/technova-common.git', branch: 'master', changelog: false, poll: false
                dir('technova-common') {
                    sh '''
                                        sudo apt-get update
                                        sudo apt-get install -y maven
                                        mvn clean install -DskipTests
                                        '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.withRegistry('', DOCKER_CREDENTIALS) {
                        docker.build("${DOCKER_IMAGE_NAME}:${env.BUILD_ID}", "--build-arg BUILD_ENV=jenkins .")
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', DOCKER_CREDENTIALS) {
                        sh "docker push $DOCKER_IMAGE_NAME:${env.BUILD_ID}"
                    }
                }
            }
        }
    }
}
