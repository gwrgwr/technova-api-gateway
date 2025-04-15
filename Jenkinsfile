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
        stage('Setup Maven Authentication') {
            steps {
                withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                    sh '''
                        mkdir -p ~/.m2
                        cat > ~/.m2/settings.xml <<EOF
                        <settings>
                            <servers>
                                <server>
                                    <id>github</id>
                                    <username>gwrgwr</username>
                                    <password>${GITHUB_TOKEN}</password>
                                </server>
                            </servers>
                        </settings>
                        EOF
                    '''
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.withRegistry('', DOCKER_CREDENTIALS) {
                        docker.build("${DOCKER_IMAGE_NAME}:${env.BUILD_ID}", '-f Dockerfile .')
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
    post {
        always {
            sh 'rm -f ~/.m2/settings.xml'
    }
}
}
