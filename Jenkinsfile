pipeline {
	agent {
	    label 'kubernetes-agent'
	}

    environment {
		DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
        DOCKER_CREDENTIALS = 'docker-hub-credentials'
        GITHUB_TOKEN = credentials('github-token')
    }

    stages {
		stage('Checkout') {
			steps {
				checkout scm
            }
        }

        stage('Gerar settings.xml') {
			steps {
				script {
					writeFile file: 'settings.xml', text: """
                            <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
                                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
                                <servers>
                                    <server>
                                        <id>github</id>
                                        <username>gwrgwr</username>
                                        <password>${GITHUB_TOKEN}</password>
                                    </server>
                                </servers>
                            </settings>
                            """
                }
            }
        }

        stage('Build Docker Image') {
			steps {
				script {
					docker.withRegistry('', DOCKER_CREDENTIALS) {
						docker.build("${DOCKER_IMAGE_NAME}", "--build-arg GITHUB_TOKEN=${GITHUB_TOKEN} -f Dockerfile .")
                    }
                }
            }
        }

        stage('Push Docker Image') {
			steps {
				script {
					docker.withRegistry('', DOCKER_CREDENTIALS) {
						sh "docker push ${DOCKER_IMAGE_NAME}"

                    }
                }
            }
        }

        stage("Deploy to Kubernetes") {
                    steps {
                        script {
                            sh "sed -i 's|IMAGE_PLACEHOLDER|${DOCKER_IMAGE_NAME}|' k8s/deployment.yaml"

                            withKubeConfig([credentialsId: 'sa-k8s-token', serverUrl: 'https://192.168.49.2:8443']) {
                                sh 'kubectl apply -f k8s/deployment.yaml'
                            }
                        }
                    }
                }
    }

    post {
		always {
			sh 'rm -f settings.xml'
        }
    }
}
