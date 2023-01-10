
node {
  stage('========== Clone repository ==========') {
    checkout scm
}

stage('Gradle Build')
  {
    sh ' ./gradlew api:clean api:build && ./gradlew chat:clean chat:build'
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