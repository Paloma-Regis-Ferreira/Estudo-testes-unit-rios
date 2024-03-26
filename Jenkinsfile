pipeline {
    agent any

    environment {
        // Defina a variável MVN para o diretório onde o Maven está montado no contêiner do Jenkins
        MVN = tool 'Maven'
    }

    stages {
        stage('Setup Maven') {
            steps {
                // Configura o Maven no contêiner do Jenkins
                script {
                    // Define a ferramenta Maven para o Jenkins
                    env.PATH = "${MVN}/bin:${env.PATH}"
                }
            }
        }
// etapa desnecessaria pois na configuração do jenkins ele ja diz onte esta o projeto para ser baixado
//         stage('Checkout') {
//             steps {
//                 // Clona o repositório do GitHub e verifica a branch main
//                 script {
//                     git branch: 'main', url: 'https://github.com/Paloma-Regis-Ferreira/Estudo-testes-unitarios.git'
//                 }
//             }
//         }

        stage('Build') {
            steps {
                // Compila o projeto Maven
                sh 'mvn clean package -DskipTests=true'
            }
        }

//no passo acima ja usou o clean. Nao usar mais e trabalhar com o mesmo binario gerado no passo anterior para os proximos stages
        stage('Test') {
            steps {
                // Executa os testes
                sh 'mvn test'
            }
        }

        stage('Test and Coverage') {
            steps {
                // Executa os testes com JaCoCo e gera relatórios de cobertura
                sh 'mvn test jacoco:report'
            }
        }

        stage('Sonar curso'){ //com as variaveis definidas no jenkins
            environment{
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL'){
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=Estudo-testes-unitarios -Dsonar.host.url=http://172.19.0.3:9000 -Dsonar.java.binaries=target"
                }
            }
        }

        stage('SonarQube Analysis') { //sem as variaveis definidas no jenkins
            steps {
                script {
                    // Endereço IP do contêiner do SonarQube
                    def sonarqubeIP = '172.19.0.3'
                    // Executa a análise do SonarQube
                    withSonarQubeEnv(serverUrl: "http://${sonarqubeIP}:9000") {
                        sh "${MVN}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=Estudo-testes-unitarios -Dsonar.projectName='Estudo-testes-unitarios'"
                    }
                }
            }
        }

        stage('Quality Gate'){
            steps{
                script {
                    def serverUrl = 'http://seu-servidor-sonarqube'
                    def taskId = waitForQualityGate()
                    def qg = waitForQualityGate() // Executa a análise no servidor do SonarQube e aguarda o resultado do Quality Gate
                    echo "Status do Quality Gate: ${qg}"
                }
            }// Define a condição para falhar o build caso o Quality Gate não seja atendido
        }

        def waitForQualityGate() {
            // Analisa a saída do scanner do SonarQube para obter o ID da tarefa
            def qg = waitForQualityGate() // Executa a análise no servidor do SonarQube e aguarda o resultado do Quality Gate
            if (qg.status != 'OK') {
                error "Failed to pass the Quality Gate. Check SonarQube dashboard for details: http://172.19.0.3:9000/dashboard?id=${env.JOB_NAME}"
            }
            return qg
        }
    }
}