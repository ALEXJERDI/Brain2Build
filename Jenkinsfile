pipeline {
    agent any

    tools {
        jdk 'JDK 17'
        maven 'Maven 3.8.8'
    }

    environment {
        HARBOR_REGISTRY = '100.103.202.82'
        HARBOR_PROJECT = 'devops-project'
        HARBOR_CREDENTIALS_ID = 'harbor-creds'
        PROJECT_NAME = 'b2b'
        MICROSERVICES = 'authservice,discoveryservice,gatewayservice,ideasservice,projectservice,serviceroom,userservice'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Checking out B2B project...'
                checkout scm
                
                sh '''
                    echo "📂 Verifying project structure..."
                    ls -la
                    
                    echo "🔍 Checking microservices..."
                    for service in authservice discoveryservice gatewayservice ideasservice projectservice serviceroom userservice; do
                        if [ -d "$service" ]; then
                            echo "✅ $service exists"
                            ls -la $service/ | head -5
                        else
                            echo "❌ $service NOT FOUND"
                        fi
                    done
                '''
            }
        }

        stage('Build Parent & Microservices') {
            steps {
                echo '🔨 Building parent POM and all microservices...'
                script {
                    // Build parent POM
                    sh 'mvn clean install -N -DskipTests'
                    
                    // Build each microservice
                    def services = env.MICROSERVICES.split(',')
                    for (service in services) {
                        echo "Building ${service}..."
                        dir(service) {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                echo '🐳 Building and pushing Docker images to Harbor...'
                script {
                    withCredentials([
                        usernamePassword(
                            credentialsId: "${HARBOR_CREDENTIALS_ID}",
                            usernameVariable: 'HARBOR_USER',
                            passwordVariable: 'HARBOR_PASS'
                        )
                    ]) {
                        sh "echo \$HARBOR_PASS | docker login ${HARBOR_REGISTRY} -u \$HARBOR_USER --password-stdin"
                        
                        def services = env.MICROSERVICES.split(',')
                        for (service in services) {
                            echo "Processing ${service}..."
                            dir(service) {
                                sh """
                                    docker build -t ${HARBOR_REGISTRY}/${HARBOR_PROJECT}/${PROJECT_NAME}-${service}:${BUILD_NUMBER} \
                                                 -t ${HARBOR_REGISTRY}/${HARBOR_PROJECT}/${PROJECT_NAME}-${service}:latest .
                                    
                                    docker push ${HARBOR_REGISTRY}/${HARBOR_PROJECT}/${PROJECT_NAME}-${service}:${BUILD_NUMBER}
                                    docker push ${HARBOR_REGISTRY}/${HARBOR_PROJECT}/${PROJECT_NAME}-${service}:latest
                                    
                                    docker rmi ${HARBOR_REGISTRY}/${HARBOR_PROJECT}/${PROJECT_NAME}-${service}:${BUILD_NUMBER} || true
                                    docker rmi ${HARBOR_REGISTRY}/${HARBOR_PROJECT}/${PROJECT_NAME}-${service}:latest || true
                                """
                            }
                        }
                        
                        sh "docker logout ${HARBOR_REGISTRY}"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Images pushed to Harbor successfully!'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed! Check logs above.'
        }
        always {
            sh 'docker image prune -f || true'
            cleanWs()
        }
    }
}