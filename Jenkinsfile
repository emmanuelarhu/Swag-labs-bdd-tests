pipeline {
	agent {
		label 'linux-agent'
	}

    environment {
		DOCKER_IMAGE = 'swag-labs-bdd-tests'
    }

    stages {
		stage('Checkout') {
			steps {
				echo 'Checking out code from GitHub...'
                checkout scm
            }
        }

        stage('Build & Test with Docker') {
			steps {
				echo 'Building Docker image and running tests...'
                script {
					// Build image and run tests in one step
                    sh '''
                        # Build Docker image
                        docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} .

                        # Run tests and copy reports out
                        docker run --name test-container-${BUILD_NUMBER} ${DOCKER_IMAGE}:${BUILD_NUMBER}

                        # Copy reports from container to Jenkins workspace
                        docker cp test-container-${BUILD_NUMBER}:/Behaviour-Driven-UI-Test/target ./

                        # Cleanup container
                        docker rm test-container-${BUILD_NUMBER}
                    '''
                }
            }
            post {
				always {
					// Clean up images to save space
                    sh "docker rmi ${DOCKER_IMAGE}:${BUILD_NUMBER} || true"
                }
            }
        }
    }

    post {
		always {
			echo 'Publishing test results...'

            // Publish HTML reports
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/cucumber-reports',
                reportFiles: 'cucumber-html-report.html',
                reportName: 'Cucumber Test Report'
            ])

            // Archive test results
            archiveArtifacts artifacts: 'target/cucumber-reports/**/*', allowEmptyArchive: true
        }

        success {
			echo 'All tests passed successfully!'
        }

        failure {
			echo 'Build failed. Check the logs for details.'
        }
    }
}