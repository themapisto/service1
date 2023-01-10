
node {
  stage('========== Clone repository ==========') {
    checkout scm
}
 stage('Ready'){
        sh "echo 'Ready to build'"
        mvnHome = tool 'Maven 3.8.6'
    }

    // mvn 빌드로 jar파일을 생성하는 stage
    stage('Build'){
        sh "echo 'Build Spring Boot Jar'"
        sh "'${mvnHome}/bin/mvn' clean package"
    }
  stage('========== Build image ==========') {
    app = docker.build("tanzu/${env.IMAGE_NAME}")
}
  stage('========== Push image ==========') {
    docker.withRegistry('https://harbor.aikoo.net', 'harbor') {
      app.push("${env.BUILD_NUMBER}")
      app.push("latest")
}

  }
}