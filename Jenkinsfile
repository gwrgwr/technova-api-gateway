def DOCKER_IMAGE_NAME = 'gwrgwr/technova-api-gateway:latest'
def GITHUB_TOKEN = credentials('github-token')

podTemplate(
    containers: [
        containerTemplate(
            name: 'kaniko',
            image: 'gcr.io/kaniko-project/executor:latest',
            command: '/busybox/sh',
            args: '-c "while true; do sleep 30; done"',
            volumeMounts: [
                mountPath: '/kaniko/.docker',
                name: 'docker-config'
            ]
        )
    ],
    volumes: [
        secretVolume(
            secretName: 'docker-config',
            mountPath: '/kaniko/.docker'
        )
    ]
) {
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
}
