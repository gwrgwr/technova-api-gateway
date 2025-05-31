def GITHUB_TOKEN = credentials('github-auth')
def POD_LABEL = 'kaniko'
    node(POD_LABEL) {
        def DOCKER_IMAGE_NAME = "gwrgwr/technova-api-gateway:${env.BUILD_ID}"
        def DOCKER_IMAGE_NAME_LATEST = "gwrgwr/technova-api-gateway:latest"
        stage('Checkout') {
            checkout scm
        }

        stage('Build with Kaniko') {
            container('kaniko') {
               sh '''#!/busybox/sh
                /kaniko/executor \
                  --context `pwd` \
                  --build-arg GITHUB_TOKEN=$GITHUB_TOKEN \
                  --dockerfile=./Dockerfile \
                  --destination ''' + DOCKER_IMAGE_NAME + ''' \
                  --destination ''' + DOCKER_IMAGE_NAME_LATEST + '''\
                '''
            }
        }

                stage('Checkout Helm Chart') {
                    git url: 'https://github.com/muriloramos-dev/technova-helm.git', branch: 'master', credentialsId: 'github-auth'
                }

                stage('Deploy to Kubernetes') {
                    container('kubectl') {
                        withKubeConfig([credentialsId: 'jenkins-token', namespace: 'jenkins', serverUrl: 'http://137.184.207.136']) {
                                    sh """
                                        helm upgrade --install technova-api-gateway ./charts/gateway/ \
                                        --values values.yaml \
                                        --values charts/gateway/values.yaml \
                                        --namespace technova \
                                        --set gateway.image.tag=${env.BUILD_ID} \
                                        --wait \
                                        --atomic
                                        """
                                }
                        }
                }
    }