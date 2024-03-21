pipeline {
    agent any

    options {
        // Define a estratégia de checkout para especificar a branch
        skipDefaultCheckout(true)
    }

    stages {
        stage('Checkout') {
            steps {
                // Clona o repositório do GitHub e verifica a branch main
                script {
                    git branch: 'main', url: 'https://github.com/Paloma-Regis-Ferreira/Estudo-testes-unitarios.git'
                }
            }
        }


        stage('Build') {
            steps {
                // Compila o projeto Maven
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                // Executa os testes
                sh 'mvn test'
            }
        }

       stage('SonarQube Analysis') {
           def mvn = tool 'Default Maven';
           withSonarQubeEnv() {
               sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=Estudo-testes-unitarios -Dsonar.projectName='Estudo-testes-unitarios'"
           }
       }

       stage('Quality Gate Check') {
           steps {
                timeout(time: 1, unit: 'HOURS') {
                    echo "Quality Gate verificado!"
                    waitForQualityGate abortPipeline: true
                }
           }
       }
    }
}
