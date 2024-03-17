pipeline {
    agent any

    environment {
        TokenSonar = credentials('c3f7a2c29dec71bfd40e3484aed3a3a80d612df5')
    }

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

        stage('SonarQube analysis') {
            steps {
                // Define o local do scanner do SonarQube
                script {
                    def scannerHome = tool 'SONAR_SCANNER'
                    // Executa a análise do SonarQube
                    echo "Token do Sonar: ${TokenSonar}"
                    withSonarQubeEnv('SONAR_LOCAL') {
                        sh "${scannerHome}/bin/sonar-scanner"
                    }
                }
                // Executa o plugin Maven SonarQube
                sh '''
                mvn sonar:sonar \
                -Dsonar.projectKey=Estudo-Testes-Unitarios \
                -Dsonar.host.url=http://localhost:9000 \
                -Dsonar.login=${TokenSonar}
                -Dsonar.java.binaries=target
                '''
            }
        }
    }
}
