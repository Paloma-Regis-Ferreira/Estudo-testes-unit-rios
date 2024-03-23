pipeline {
    agent any

    environment {
        // Defina a variável M2_HOME para o diretório onde o Maven está montado no contêiner do Jenkins
        M2_HOME = '/usr/share/maven'
    }

    stages {
        stage('Setup Maven') {
            steps {
                // Configura o Maven no contêiner do Jenkins
                script {
                    // Define a ferramenta Maven para o Jenkins
                    def mvnHome = tool 'Maven'
                    // Adiciona o Maven ao PATH
                    env.PATH = "${mvnHome}/bin:${env.PATH}"
                }
            }
        }

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

        stage('Test and Coverage') {
            steps {
                // Executa os testes com JaCoCo e gera relatórios de cobertura
                sh 'mvn clean test jacoco:report -Djacoco.excludes=**/barriga/**/*.class,**/exceptions/**/*.class,**/infra/**/*.class'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Define o Maven como a ferramenta padrão
                    def mvn = tool 'Maven'
                    // Endereço IP do contêiner do SonarQube
                    def sonarqubeIP = '172.19.0.3'
                    // Executa a análise do SonarQube
                    withSonarQubeEnv(serverUrl: "http://${sonarqubeIP}:9000") {
                        sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=Estudo-testes-unitarios -Dsonar.projectName='Estudo-testes-unitarios'"
                    }
                }
            }
        }

        stage('Quality Gate'){
            steps{
                sleep(5)
                timeout(time: 1, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
                }
            }// Define a condição para falhar o build caso o Quality Gate não seja atendido
        }
    }
}