pipeline {
    agent any

    environment {
        GIT_URL = 'https://github.com/ww5702/Skala-Stock-Server.git'
        GIT_BRANCH = 'master'
        GIT_ID = 'skala-github-id'
        GIT_USER_NAME = 'ww5702'
        GIT_USER_EMAIL = 'wow5702@naver.com'
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

        stage('Setup Java 21') {
            steps {
                sh '''
                    echo "🛠️ Java 21 다운로드"
                    curl -L -o openjdk-21.tar.gz https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.1%2B12/OpenJDK21U-jdk_aarch64_linux_hotspot_21.0.1_12.tar.gz
                    rm -rf jdk-21
                    mkdir jdk-21
                    tar -xzf openjdk-21.tar.gz --strip-components=1 -C jdk-21
                    export JAVA_HOME=$PWD/jdk-21
                    export PATH=$JAVA_HOME/bin:$PATH
                    java -version
                '''
            }
        }

        stage('Build with Gradle') {
            steps {
                sh '''
                    chmod +x ./gradlew
                    ./gradlew clean build
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Docker Build & Push with Tag') {
            steps {
                script {
                    def hashcode = sh(
                        script: "date +%s%N | sha256sum | cut -c1-12",
                        returnStdout: true
                    ).trim()

                    def FINAL_IMAGE_TAG = "${IMAGE_TAG}-${BUILD_NUMBER}-${hashcode}"
                    echo "📦 Final Image Tag: ${FINAL_IMAGE_TAG}"

                    docker.withRegistry("https://${IMAGE_REGISTRY}", "${DOCKER_CREDENTIAL_ID}") {
                        def appImage = docker.build("${IMAGE_REGISTRY}/${IMAGE_NAME}:${FINAL_IMAGE_TAG}", "--platform=linux/amd64 .")
                        appImage.push()
                    }

                    env.FINAL_IMAGE_TAG = FINAL_IMAGE_TAG
                }
            }
        }

        stage('Update deploy.yaml and Git Push') {
            steps {
                script {
                    def newImageLine = "        image: ${env.IMAGE_REGISTRY}/${env.IMAGE_NAME}:${env.FINAL_IMAGE_TAG}"
                    def gitRepoPath = env.GIT_URL.replaceFirst(/^https?:\/\//, '')

                    sh """
                        sed -i 's|^[[:space:]]*image:.*\$|${newImageLine}|g' ./k8s/deploy.yaml
                        cat ./k8s/deploy.yaml
                    """

                    sh """
                        git config user.name "$GIT_USER_NAME"
                        git config user.email "$GIT_USER_EMAIL"
                        git add ./k8s/deploy.yaml || true
                    """

                    withCredentials([usernamePassword(credentialsId: "${env.GIT_ID}", usernameVariable: 'GIT_PUSH_USER', passwordVariable: 'GIT_PUSH_PASSWORD')]) {
                        sh """
                            if ! git diff --cached --quiet; then
                                git commit -m "[AUTO] Update deploy.yaml with image ${env.FINAL_IMAGE_TAG}"
                                git remote set-url origin https://${GIT_PUSH_USER}:${GIT_PUSH_PASSWORD}@${gitRepoPath}
                                git push origin ${env.GIT_BRANCH}
                            else
                                echo "No changes to commit."
                            fi
                        """
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
