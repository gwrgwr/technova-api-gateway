def DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
def GITHUB_TOKEN = credentials('github-auth')
// def POD_LABEL = 'kaniko'

podTemplate(
    inheritFrom: 'default',
    containers: [
            containerTemplate(
                name: 'kaniko',
                image: 'gcr.io/kaniko-project/executor:latest',
                command: '',
                args: '',
                ttyEnabled: true
            ),
            containerTemplate(
                name: 'kubectl',
                image: 'bitnami/kubectl:latest',
                command: 'cat',
                ttyEnabled: true
            )
        ]
    ) {

    node(POD_LABEL) {

        stage('Test Kubernetes') {
                     container('kubectl') {
                         withKubeConfig([namespace: 'jenkins']) {
                             sh '''
                                 kubectl version --client
                                 kubectl get pods
                             '''
                         }
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
}