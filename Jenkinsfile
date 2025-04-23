def DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
def GITHUB_TOKEN = credentials('github-auth')
def POD_LABEL = 'kaniko'

node(POD_LABEL) {
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
}
