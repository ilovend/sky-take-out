pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean test package'
      }
    }

    stage('Artifacts') {
      steps {
        archiveArtifacts 'sky-server/target/*.jar,sky-pojo/target/*.jar'
      }
    }

  }
}