pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package install'
      }
    }

    stage('Package') {
      steps {
        archiveArtifacts 'sky-server/target/*.jar'
      }
    }

  }
}