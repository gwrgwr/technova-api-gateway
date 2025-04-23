def DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
def GITHUB_TOKEN = credentials('github-auth')
def POD_LABEL = 'kaniko'

node(POD_LABEL) {
    stage('Checkout') {
        checkout scm
    }

    stage('Prepare settings.xml') {
        writeFile file: 'settings.xml', text: """
<settings>
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

    stage('Build with Kaniko') {
        container('kaniko') {
            sh '''#!/busybox/sh
                /kaniko/executor \
                  --context `pwd` \
                  --dockerfile=./Dockerfile \
                  --destination gwrgwr/technova-api-gateway:latest \
                  --build-arg GITHUB_TOKEN=${GITHUB_TOKEN}
            '''
        }
    }

    stage('Cleanup') {
        sh 'rm -f settings.xml'
    }
}
