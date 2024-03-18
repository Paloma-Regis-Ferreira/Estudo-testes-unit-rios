pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('c3f7a2c29dec71bfd40e3484aed3a3a80d612df5')
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
                // Executa a análise do SonarQube
                script {
                    // Define o local do scanner do SonarQube
                    def scannerHome = tool 'SONAR_SCANNER'
                    withSonarQubeEnv('SONAR_LOCAL') {
                        sh "${scannerHome}/bin/sonar-scanner"
                    }
                }
                script {
                    def tokenSonar = env.SONAR_TOKEN
                    // Executa o plugin Maven SonarQube
                    sh '''
                    mvn sonar:sonar \
                    -Dsonar.projectKey=Estudo-Testes-Unitarios \
                    -Dsonar.host.url=http://localhost:9000 \
                    -Dsonar.login=${tokenSonar}
                    -Dsonar.java.binaries=target
                    '''
                }
            }
        }
    }
}
