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
            echo 'Publishing test results and reports...'

            // Archive all artifacts
            archiveArtifacts artifacts: 'target/**/*', allowEmptyArchive: true

            // Publish parsed Cucumber JSON report
            cucumber(
                buildStatus: 'UNCHANGED',
                fileIncludePattern: 'target/cucumber-reports/cucumber.json',
                sortingMethod: 'ALPHABETICAL'
            )

            // Publish HTML report
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/cucumber-reports',
                reportFiles: 'cucumber-html-report.html',
                reportName: 'Cucumber Test Report',
                reportTitles: 'BDD Test Results'
            ])
        }

        success {
            slackSend(
                color: 'good', // green
                message: """
                ✅ *BUILD SUCCESS*
                *Job:* ${env.JOB_NAME}
                *Build:* #${env.BUILD_NUMBER}
                *Status:* SUCCESS
                *Triggered By:* ${currentBuild.getBuildCauses()[0].shortDescription}

                🔗 <${env.BUILD_URL}|Build Details> | <${env.BUILD_URL}console|Console Output> | <${env.report}allure|Cucumber Report>
                    """
                )

            emailext(
                subject: "✅ SUCCESSFUL BUILD: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <html>
                    <body style="font-family: Arial, sans-serif; color: #333;">
                        <h2 style="color: green;">✅ Build Successful!</h2>

                        <p>Hello Team,</p>
                        <p>The build <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> completed <span style="color: green; font-weight: bold;">SUCCESSFULLY</span>.</p>

                        <h3>🔍 Build Details</h3>
                        <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse;">
                            <tr><th align="left">Job Name</th><td>${env.JOB_NAME}</td></tr>
                            <tr><th align="left">Build Number</th><td>${env.BUILD_NUMBER}</td></tr>
                            <tr><th align="left">Status</th><td style="color: green;"><b>SUCCESS</b></td></tr>
                            <tr><th align="left">Triggered By</th><td>${currentBuild.getBuildCauses()[0].shortDescription}</td></tr>
                        </table>

                        <h3>📎 Links</h3>
                        <ul>
                            <li><a href="${env.BUILD_URL}" style="color: blue;">Jenkins Build Details</a></li>
                            <li><a href="${env.BUILD_URL}console" style="color: blue;">Console Output</a></li>
                            <li><a href="${env.report}allure" style="color: blue;">Cucumber Report</a></li>
                        </ul>

                        <p>Regards,<br><b>Jenkins CI</b></p>
                    </body>
                    </html>
                """,
                mimeType: 'text/html',
                to: "notebooks8.8.8.8@gmail.com"
            )

        }

        failure {
            slackSend(
                color: 'danger', // red
                message: """
                ❌ *BUILD FAILED*
                *Job:* ${env.JOB_NAME}
                *Build:* #${env.BUILD_NUMBER}
                *Status:* FAILED
                *Triggered By:* ${currentBuild.getBuildCauses()[0].shortDescription}

                🔗 <${env.BUILD_URL}|Build Details> | <${env.BUILD_URL}console|Console Output> | <${env.report}allure|Cucumber Report>
                    """
                )

            emailext(
                subject: "❌ FAILED BUILD: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <html>
                    <body style="font-family: Arial, sans-serif; color: #333;">
                        <h2 style="color: red;">❌ Build Failed!</h2>

                        <p>Hello Team,</p>
                        <p>The build <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> has <span style="color: red; font-weight: bold;">FAILED</span>.</p>

                        <h3>🔍 Build Details</h3>
                        <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse;">
                            <tr><th align="left">Job Name</th><td>${env.JOB_NAME}</td></tr>
                            <tr><th align="left">Build Number</th><td>${env.BUILD_NUMBER}</td></tr>
                            <tr><th align="left">Status</th><td style="color: red;"><b>FAILED</b></td></tr>
                            <tr><th align="left">Triggered By</th><td>${currentBuild.getBuildCauses()[0].shortDescription}</td></tr>
                        </table>

                        <h3>📎 Links</h3>
                        <ul>
                            <li><a href="${env.BUILD_URL}" style="color: blue;">Jenkins Build Details</a></li>
                            <li><a href="${env.BUILD_URL}console" style="color: blue;">Console Output</a></li>
                            <li><a href="${env.report}allure" style="color: blue;">Cucumber Report</a></li>
                        </ul>

                        <p>Regards,<br><b>Jenkins CI</b></p>
                    </body>
                    </html>
                """,
                mimeType: 'text/html',
                to: "notebooks8.8.8.8@gmail.com"
            )

        }

        unstable {
            slackSend(
                color: 'warning', // yellow
                message: """
                ⚠️ *BUILD UNSTABLE*
                *Job:* ${env.JOB_NAME}
                *Build:* #${env.BUILD_NUMBER}
                *Status:* UNSTABLE
                *Triggered By:* ${currentBuild.getBuildCauses()[0].shortDescription}

                🔗 <${env.BUILD_URL}|Build Details> | <${env.BUILD_URL}console|Console Output> | <${env.report}|Cucumber Report>
                    """
                )

            emailext(
                subject: "⚠️UNSTABLE BUILD: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    <html>
                    <body style="font-family: Arial, sans-serif; color: #333;">
                        <h2 style="color: orange;">⚠️ Build Unstable!</h2>

                        <p>Hello Team,</p>
                        <p>The build <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> has finished with an <span style="color: orange; font-weight: bold;">UNSTABLE</span> status.</p>

                        <h3 style="color: #555;">🔍 Build Details</h3>
                        <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse;">
                            <tr><th align="left">Job Name</th><td>${env.JOB_NAME}</td></tr>
                            <tr><th align="left">Build Number</th><td>${env.BUILD_NUMBER}</td></tr>
                            <tr><th align="left">Status</th><td style="color: orange;"><b>UNSTABLE</b></td></tr>
                            <tr><th align="left">Triggered By</th><td>${currentBuild.getBuildCauses()[0].shortDescription}</td></tr>
                        </table>

                        <h3 style="color: #555;">📎 Links</h3>
                        <ul>
                            <li><a href="${env.BUILD_URL}" style="color: blue;">Jenkins Build Details</a></li>
                            <li><a href="${env.BUILD_URL}console" style="color: blue;">Console Output</a></li>
                            <li><a href="${env.report}allure" style="color: blue;">Cucumber Report</a></li>
                        </ul>

                        <p>Regards,<br><b>Jenkins CI</b></p>
                        <p>
                    </body>
                    </html>
                """,
                mimeType: 'text/html',
                to: "notebooks8.8.8.8@gmail.com"
            )
        }
    }
}