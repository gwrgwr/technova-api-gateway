def DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
def GITHUB_TOKEN = credentials('github-auth')
def POD_LABEL = 'kaniko'
    node(POD_LABEL) {

        stage('Checkout') {
            checkout scm
            echo "Commit completo: ${env.GIT_COMMIT}"
        }

        stage('Debug Env') {
            sh 'env | sort'
        }


        stage('Build with Kaniko') {
            container('kaniko') {
                sh '''#!/busybox/sh
                                /kaniko/executor \
                                  --context `pwd` \
                                  --dockerfile=./Dockerfile \
                                  --destination ''' + DOCKER_IMAGE_NAME + ''' \
                                  --build-arg GITHUB_TOKEN=$GITHUB_TOKEN
                            '''
            }
        }

        stage('Deploy to Kubernetes') {
            container('kubectl') {
                withKubeConfig([credentialsId: 'jenkins-token', namespace: 'jenkins', serverUrl: 'https://192.168.49.2:8443']) {
                            sh '''#!/bin/sh
                                                    kubectl -n technova set image deployment/technova-api-gateway technova-api-gateway=''' + DOCKER_IMAGE_NAME + '''
                                                    kubectl -n technova rollout status deployment/technova-api-gateway
                                                '''
                        }
                }
        }
    }