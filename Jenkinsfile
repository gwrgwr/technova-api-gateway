def DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
def GITHUB_TOKEN = credentials('github-token')
def POD_LABEL = 'kaniko'
node(POD_LABEL) {
        stage('Checkout') {
            checkout scm
        }

        stage('Build with Kaniko') {
            container('kaniko') {
                sh '''#!/busybox/sh
                    /kaniko/executor --context `pwd` --dockerfile=./Dockerfile --destination gwrgwr/technova-api-gateway:latest
                '''
            }
        }

        stage('Cleanup') {
            sh 'rm -f settings.xml'
        }
    }
