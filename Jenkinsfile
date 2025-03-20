pipeline {
    agent any

    environment {
        KUBE_NAMESPACE = "jenkins"
        KUBE_CONFIG = "$HOME/.kube/config" // Ensure Jenkins has access
    }

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    git branch: 'main', url: 'https://github.com/your-repo/my-java-app.git'
                }
            }
        }

        stage('Apply RBAC Permissions') {
            steps {
                script {
                    sh 'kubectl apply -f k8s-manifests/jenkins-rbac.yaml'
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    sh 'kubectl apply -f k8s-manifests/deployment.yaml'
                    sh 'kubectl apply -f k8s-manifests/service.yaml'
                }
            }
        }

        stage('Check Deployment Status') {
            steps {
                script {
                    sh 'kubectl rollout status deployment my-java-app -n jenkins'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Deployment Successful!'
        }
        failure {
            echo '❌ Deployment Failed!'
        }
    }
}
