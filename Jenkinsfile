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

//         stage('Sonar curso'){ //com as variaveis definidas no jenkins
//             environment{
//                 scannerHome = tool 'SONAR_SCANNER'
//             }
//             steps{
//                 withSonarQubeEnv('SONAR_LOCAL'){
//                     sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=Estudo-testes-unitarios -Dsonar.host.url=http://172.19.0.3:9000 -Dsonar.java.binaries=target"
//                 }
//             }
//         }

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

        stage('Quality Gate') {
            steps {
                script {
                    sleep(60)
                    timeout(time: 2, unit: 'MINUTES') {
                        // Executa o comando waitForQualityGate para esperar pelo resultado do Quality Gate
                        // Aborta o pipeline se o Quality Gate falhar
                        def qgResult = waitForQualityGate abortPipeline: true
                        echo "Status do Quality Gate: ${qgResult}"
                        if (qgResult != 'OK') {
                            // Se o Quality Gate não passou, capturamos a causa e a exibimos no console do Jenkins
                            def qgDetails = waitForQualityGate getDetails: true
                            echo "Detalhes do Quality Gate: ${qgDetails}"
                            error "Failed to pass the Quality Gate. Check SonarQube dashboard for details: http://172.19.0.3:9000/dashboard?id=${env.JOB_NAME}"
                        }
                    }
                }
            }
        }
    }
}