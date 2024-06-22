def call() {
    pipeline {
        agent any

        stages {
            stage('Checkout') {
                steps {
                    // Checkout the code from the Git repository
                    git url: "https://github.com/rezacr588/userservice.git", branch: 'main'
                }
            }

            stage('Build') {
                steps {
                    // Run the Maven build
                    sh './mvnw clean install'
                }
            }

            stage('Test') {
                steps {
                    // Run the Maven tests
                    sh './mvnw test'
                }
            }

            stage('Deploy') {
                steps {
                    // Build and push the Docker image
                    sh "docker build -t your-docker-repo/userservice:latest ."
                    sh "docker push your-docker-repo/userservice:latest"
                }
            }
        }

        post {
            always {
                // Clean up the workspace after the build
                cleanWs()
            }
            success {
                // Print a success message
                echo 'Build and deploy succeeded!'
            }
            failure {
                // Print a failure message
                echo 'Build or deploy failed!'
            }
        }
    }
}