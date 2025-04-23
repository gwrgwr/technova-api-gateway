def DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
def GITHUB_TOKEN = credentials('github-auth')
def POD_LABEL = 'kaniko'

podTemplate(inheritFrom: 'default')
node(POD_LABEL) {

    stage('Test Kubernetes') {
        withKubeConfig([namespace: 'jenkins']) {
            sh '''#!/bin/sh
                            kubectl version --client
                            kubectl get pods
                        '''
        }
    }



    stage('Checkout') {
        checkout scm
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
            withKubeConfig([credentialsId: 'kubeconfig']) {
                        sh '''#!/bin/sh
                                                kubectl version --client
                                                kubectl set image deployment/technova-api-gateway technova-api-gateway=''' + DOCKER_IMAGE_NAME + '''
                                                kubectl rollout status deployment/technova-api-gateway
                                            '''
                    }
            }
    }
}
