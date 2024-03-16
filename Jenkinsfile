pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Clona o repositório do GitHub
                git branch: 'main', 'https://github.com/Paloma-Regis-Ferreira/Estudo-testes-unitarios.git'
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
    }
//
//     post {
//         always {
//             // Publica os resultados dos testes
//             junit '**/target/surefire-reports/*.xml'
//         }
//     }
}