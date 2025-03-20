pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building the Java application...'
                sh 'mvn clean package'
            }
        }

        stage('Docker Build & Push') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t us-central1-docker.pkg.dev/civic-network-453215-s8/my-docker-repo/my-java-app:latest .'
                echo 'Pushing to Google Artifact Registry...'
                sh 'docker push us-central1-docker.pkg.dev/civic-network-453215-s8/my-docker-repo/my-java-app:latest'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Deploying to GKE...'
                sh 'kubectl apply -f k8s/ -n prod'
            }
        }
    }
}
