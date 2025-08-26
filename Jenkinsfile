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

        stage('Build Docker Image') {
			steps {
				echo 'Building Docker image...'
				script {
					sh '''
					# Build Docker image
                    docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} .
					'''
				}
			}

        }

        stage('Running Test with Docker') {
			steps {
				echo 'Running tests on container...'
                script {
                    sh '''
                        # Run tests and copy reports out
                        docker run --name test-container-${BUILD_NUMBER} ${DOCKER_IMAGE}:${BUILD_NUMBER}

                        # Copy reports from container to Jenkins workspace
                        docker cp test-container-${BUILD_NUMBER}:/Behaviour-Driven-UI-Test/target ./

                        # Cleanup container
                        #docker rm test-container-${BUILD_NUMBER}
                    '''
                }
            }
            post {
				always {
					// Clean up images to save space
                    //sh "docker rmi ${DOCKER_IMAGE}:${BUILD_NUMBER} || true"
                }
            }
        }
    }

    post {
		always {
			echo 'Publishing test results and reports...'

            // Archive all artifacts
            archiveArtifacts artifacts: 'target/**/*', allowEmptyArchive: true

            // Publish HTML reports with corrected configuration
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/cucumber-reports/',
                reportFiles: 'cucumber-html-report.html',
                reportName: 'Cucumber Test Report',
                reportTitles: 'BDD Test Results'
            ])
        }

        success {
			echo 'All tests passed successfully!'

            // Slack notification for success
            script {
				try {
					slackSend(
                        channel: "${SLACK_CHANNEL}",
                        color: 'good',
                        message: """
                        ✅ *BDD Tests - SUCCESS*

                        *Project:* ${env.JOB_NAME}
                        *Build:* #${env.BUILD_NUMBER}
                        *Branch:* ${env.GIT_BRANCH}
                        *Duration:* ${currentBuild.durationString}

                        All tests passed successfully!

                        📊 *Reports:* ${env.BUILD_URL}Cucumber_Test_Report/
                        """
                    )
                } catch (Exception e) {
					echo "Slack notification failed: ${e.getMessage()}"
                }
            }
        }

        failure {
			echo 'Build failed. Sending notifications...'

            // Slack notification for failure
            script {
				try {
					slackSend(
                        channel: "${SLACK_CHANNEL}",
                        color: 'danger',
                        message: """
                        ❌ *BDD Tests - FAILED*

                        *Project:* ${env.JOB_NAME}
                        *Build:* #${env.BUILD_NUMBER}
                        *Branch:* ${env.GIT_BRANCH}
                        *Duration:* ${currentBuild.durationString}

                        Some tests failed. Please check the logs.

                        🔍 *Build Logs:* ${env.BUILD_URL}console
                        📊 *Reports:* ${env.BUILD_URL}Cucumber_Test_Report/
                        """
                    )
                } catch (Exception e) {
					echo "Slack notification failed: ${e.getMessage()}"
                }
            }

            // Email notification for failure
            script {
				try {
					emailext(
                        subject: "❌ BDD Tests Failed - Build #${env.BUILD_NUMBER}",
                        body: """
                        <h2>BDD Test Automation - Build Failed</h2>

                        <p><strong>Project:</strong> ${env.JOB_NAME}</p>
                        <p><strong>Build Number:</strong> #${env.BUILD_NUMBER}</p>
                        <p><strong>Branch:</strong> ${env.GIT_BRANCH}</p>
                        <p><strong>Duration:</strong> ${currentBuild.durationString}</p>

                        <h3>📊 Links:</h3>
                        <ul>
                            <li><a href="${env.BUILD_URL}console">Build Console Logs</a></li>
                            <li><a href="${env.BUILD_URL}Cucumber_Test_Report/">Test Reports</a></li>
                        </ul>

                        <p>Please check the console logs and test reports for details.</p>
                        """,
                        to: "${EMAIL_RECIPIENTS}",
                        mimeType: 'text/html'
                    )
                } catch (Exception e) {
					echo "Email notification failed: ${e.getMessage()}"
                }
            }
        }

        unstable {
			echo 'Tests completed with some failures.'

            // Slack notification for unstable build
            script {
				try {
					slackSend(
                        channel: "${SLACK_CHANNEL}",
                        color: 'warning',
                        message: """
                        ⚠️ *BDD Tests - UNSTABLE*

                        *Project:* ${env.JOB_NAME}
                        *Build:* #${env.BUILD_NUMBER}
                        *Branch:* ${env.GIT_BRANCH}
                        *Duration:* ${currentBuild.durationString}

                        Some tests failed but build completed.

                        📊 *Reports:* ${env.BUILD_URL}Cucumber_Test_Report/
                        """
                    )
                } catch (Exception e) {
					echo "Slack notification failed: ${e.getMessage()}"
                }
            }
        }
    }


    //post {
	//	always {
	//		echo 'Publishing test results...'
	//
    //        // Publish HTML reports
    //        publishHTML([
    //            allowMissing: false,
    //            alwaysLinkToLastBuild: true,
    //            keepAll: true,
    //            reportDir: 'target/cucumber-reports',
    //            reportFiles: 'cucumber-html-report.html',
    //            reportName: 'Cucumber Test Report'
    //        ])
	//
    //        // Archive test results
    //        archiveArtifacts artifacts: 'target/cucumber-reports/**/*', allowEmptyArchive: true
    //    }
	//
    //    success {
	//		echo 'All tests passed successfully!'
    //    }
	//
    //    failure {
	//		echo 'Build failed. Check the logs for details.'
    //    }
    //}
}