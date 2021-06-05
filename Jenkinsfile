pipeline {
    agent any
    
    stages {

        stage('Unit Test') {
            steps {
                script {           
                    def unitTestImage = docker.build("unit-test-image", "-f Dockerfile.unittest .")
                    unitTestImage.inside() {
                        sh "mvn test"
                    }
                    junit allowEmptyResults: true, testResults: "target/surefire-reports/*.xml"
                    archiveArtifacts artifacts: "target/surefire-reports/*.xml", fingerprint: true, allowEmptyArchive: true
                }
            }
        }

        stage('Build') {
            steps {
                script {           
                    def buildImage = docker.build("quizzz", "-f Dockerfile .")
                    buildImage.inside() {
                        sh "mvn install"
                    }
                    stash name: 'quiz_jar', includes: "target/QuizZz-0.0.1-QuizApp.jar"
                }
            }
        }

        stage('Push Image to docker hub') {
            steps {
                script {           
                    withCredentials([usernamePassword(
                        credentialsId: "docker-hub", 
                        passwordVariable: 'DOCKER_PASSWORD', 
                        usernameVariable: 'DOCKER_USERNAME')]) {
                            sh """
                                docker login --username ${DOCKER_USERNAME} --password ${DOCKER_PASSWORD}
                                docker image tag quizzz:latest ${DOCKER_USERNAME}/quizzz
                                docker push ${DOCKER_USERNAME}/quizzz
                            """
                        }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def cfOrg = "aa15ac2atrial"
                    def cfApiEndpoint = "https://api.cf.eu10.hana.ondemand.com"
                    unstash name: "quiz_jar"
                    withCredentials([usernamePassword(
                        credentialsId: "cf-trial", 
                        passwordVariable: 'CF_PASS', 
                        usernameVariable: 'CF_USER')]) {
                            sh """
                                cf --version
                                cf login -a ${cfApiEndpoint} -o ${cfOrg} -u ${CF_USER} -p ${CF_PASS}
                                cf push quiz_app -p target/QuizZz-0.0.1-QuizApp.jar
                            """
                        }
                }
            }
        }
    }

    post {
        always {
            script {   
                println "----------- Pipeline Finished -------------"    
                deleteDir()
            }
        }
    }
}