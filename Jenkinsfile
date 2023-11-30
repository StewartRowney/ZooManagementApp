pipeline {
        agent any
        options {
            skipStagesAfterUnstable()
        }
        stages {
            stage('Build') {
                steps {
                    sh 'mvn -B -DskipTests clean package'
                }
            }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
//                        sh "${tool('sonar-scanner')}/bin/sonar-scanner -Dsonar.projectKey=myProjectKey -Dsonar.projectName=myProjectName"
                        sh 'mvn clean package sonar:sonar'
                    }
                }
            }
        }
        stage("Quality gate") {
                            steps {
                                waitForQualityGate abortPipeline: true
                            }
        }
       stage('Deploy') {
            steps {
//                sh './jenkins/scripts/deploy.sh'
                echo 'Deployment successful!!!'

            }
       }
    }
}