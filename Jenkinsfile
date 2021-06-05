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
                    archiveArtifacts artifacts: "target/surefire-reports/*.xml", fingerprint: true, allowEmptyArchive: true
                }
            }
        }

        stage('Build') {
            steps {
                script {           
                    def buildImage = docker.build("quizzz", "-f Dockerfile .")
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