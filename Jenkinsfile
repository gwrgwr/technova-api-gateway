pipeline {
	agent {
     	label 'kaniko-agent'
     }

	environment {
		DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
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

		stage('Build and Push Docker Image (Kaniko)') {
			steps {
				container('kaniko') {
					script {
						sh """
						/kaniko/executor \
						  --context=${WORKSPACE} \
						  --dockerfile=Dockerfile \
						  --destination=${DOCKER_IMAGE_NAME} \
						  --build-arg=GITHUB_TOKEN=${GITHUB_TOKEN}
						"""
					}
				}
			}
		}

		stage('Deploy to Kubernetes') {
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
