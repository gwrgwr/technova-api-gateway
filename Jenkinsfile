pipeline {
    agent any
    environment {
        DOCKER_IMAGE_NAME = 'gwrgwr/murilo.ramos'
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
                        docker.build("${DOCKER_IMAGE_NAME}:api-gateway-${env.BUILD_ID}", "--build-arg GITHUB_TOKEN=${GITHUB_TOKEN} -f Dockerfile .")
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', DOCKER_CREDENTIALS) {
                        sh "docker push ${DOCKER_IMAGE_NAME}:api-gateway-${env.BUILD_ID}"
                    }
                }
            }
        }

        agent {
            kubernetes {
                yamlFile 'k8s/deployment.yaml'
        }
    }

        //stage ('Deploy to Kubernetes') {
        //    steps {
        //        script {
        //            def deploymentName = "api-gateway-${env.BUILD_ID}"
        //            def imageName = "${DOCKER_IMAGE_NAME}:api-gateway-${env.BUILD_ID}"
        //
        //            sh """
        //                kubectl set image deployment/${deploymentName} ${deploymentName}=${imageName}
        //                kubectl rollout status deployment/${deploymentName}
        //            """
        //        }
        //    }
        //}
    }

    post {
        always {
            sh 'rm -f settings.xml'
        }
    }
}
