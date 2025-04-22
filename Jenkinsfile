pipeline {
    agent none  // Não usa o agente global, pois vamos usar o podTemplate

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

        stage('Build with Kaniko') {
            steps {
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
                    container(name: 'kaniko', shell: '/busybox/sh') {
                        sh '''#!/busybox/sh
                            /kaniko/executor --context `pwd` --dockerfile=./Dockerfile --destination gwrgwr/technova-api-gateway:latest
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                // Colocando o 'rm' no bloco 'script' para garantir que seja executado dentro do contexto
                sh 'rm -f settings.xml'
            }
        }
    }
}
