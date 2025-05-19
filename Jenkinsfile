pipeline {
    agent any

    environment {
        GIT_URL = 'https://github.com/ww5702/Skala-Stock-Server.git'
        GIT_BRANCH = 'master'
        GIT_ID = 'skala-github-id'
        IMAGE_REGISTRY = 'amdp-registry.skala-ai.com/skala25a'
        IMAGE_NAME = 'sk047-stock-backend'
        IMAGE_TAG = '1.0.0'
        DOCKER_CREDENTIAL_ID = 'skala-image-registry-id'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: "${GIT_BRANCH}",
                    url: "${GIT_URL}",
                    credentialsId: "${GIT_ID}"
            }
        }

        stage('Build with Gradle + Java 21') {
            steps {
                sh '''
                    echo "🛠️ ARM64용 Java 21 다운로드"
                    curl -L -o openjdk-21.tar.gz https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.1%2B12/OpenJDK21U-jdk_aarch64_linux_hotspot_21.0.1_12.tar.gz
        
                    echo "🧹 기존 디렉토리 정리"
                    rm -rf jdk-21
        
                    echo "📦 압축 해제 → jdk-21"
                    mkdir jdk-21
                    tar -xzf openjdk-21.tar.gz --strip-components=1 -C jdk-21
        
                    export JAVA_HOME=$PWD/jdk-21
                    export PATH=$JAVA_HOME/bin:$PATH
        
                    echo "✅ java -version 확인"
                    java -version
        
                    echo "⚙️ Gradle 빌드"
                    chmod +x ./gradlew
                    ./gradlew clean build -x test
                '''
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry("https://${IMAGE_REGISTRY}", "${DOCKER_CREDENTIAL_ID}") {
                        def appImage = docker.build("${IMAGE_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}", "--platform=linux/amd64 .")
                        appImage.push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    kubectl apply -f ./k8s
                    kubectl rollout status deployment/sk047-stock-backend
                '''
            }
        }
    }
}
