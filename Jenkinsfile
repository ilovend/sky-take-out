pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package'
      }
    }

    stage('Package') {
      steps {
        archiveArtifacts 'sky-server/target/*.jar'
      }
    }

  }
}