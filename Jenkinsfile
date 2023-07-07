pipeline {
    agent any
    
    environment {
        registry="lorexto/demo-boot-mb"
        registryCredential="docker-hub"
    }

    stages {
      
        stage('Build Artifact') {
            steps {
              sh "mvn clean package -DskipTests=true"
              archive 'target/*.jar' //so that they can be downloaded later
            }
        }
        
       stage('Unit Tests - JUnit and Jacoco') {
          steps {
            sh "mvn test"
          }
          post {
            always {
              junit 'target/surefire-reports/*.xml'
              jacoco execPattern: 'target/jacoco.exec'
            }
          }
        }
        
        stage('Docker Build and Push') {
          steps {
            withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
              sh 'printenv'
              sh 'docker build -t $registry:$BUILD_NUMBER .'
              sh 'docker push $registry:$BUILD_NUMBER'

            }
          }
        }
        
        stage('Remove unused image'){
            steps{
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
        
        
         stage('Deploy to test env'){
            steps{
                sh "docker stop demo-app || true"
                sh "docker rm demo-app || true"
                sh "docker run -d -p8180:8080  --name demo-app $registry:$BUILD_NUMBER"
            }
        }

        stage('Prod env') {
            steps {
                input 'do you approve deployment ?'
                echo 'Going into production'
            }
        }

         stage('Deploy to prod env'){
            steps{
                sh "docker  -H 13.38.93.152 stop demo-app || true"
                sh "docker  -H 13.38.93.152 rm demo-app || true"
                sh "docker  -H 13.38.93.152 run -d -p80:8080  --name demo-app $registry:$BUILD_NUMBER"
            }
        }
        
        
    }
}
