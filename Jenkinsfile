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
        stage('Build Docker Image') {
			steps {
				script {
					def customImage = docker.build("$DOCKER_IMAGE_NAME:${env.BUILD_ID}", "-f Dockerfile .")
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
