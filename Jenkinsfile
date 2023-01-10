
node {
  stage('========== Clone repository ==========') {
    checkout scm
}

stage('Gradle Build')
  {
    sh 'gradle clean build -x test -b build-server.gradle'
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