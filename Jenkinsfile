pipeline {
    agent any
    environment {
        DOCKER_IMAGE_NAME = 'gwrgwr/murilo.ramos'
        DOCKER_REGISTRY = 'https://index.docker.io/v1/'
        MAVEN_SETTINGS = "${env.WORKSPACE}/settings.xml"
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
                    sh """
                        cat > ${MAVEN_SETTINGS} <<EOF
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
                    """
                }
            }
        }

        stage('Build with Maven') {
            steps {
                sh "mvn -s ${MAVEN_SETTINGS} clean package"  // Build your Java application first
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.withRegistry(DOCKER_REGISTRY, DOCKER_CREDENTIALS) {
                        dockerImage = docker.build(
                            "${DOCKER_IMAGE_NAME}:${env.BUILD_ID}",
                            "--build-arg MAVEN_SETTINGS=${MAVEN_SETTINGS} -f Dockerfile ."
                        )
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry(DOCKER_REGISTRY, DOCKER_CREDENTIALS) {
                        dockerImage.push()
                        // Optional: Push as latest
                        dockerImage.push('latest')
                    }
                }
            }
        }
    }
    post {
        always {
            sh "rm -f ${MAVEN_SETTINGS}"  // Clean up settings file
            script {
                // Clean up Docker images
                sh "docker rmi ${DOCKER_IMAGE_NAME}:${env.BUILD_ID} || true"
                sh "docker rmi ${DOCKER_IMAGE_NAME}:latest || true"
            }
        }
    }
}