<template>
  <v-container fill-height fluid>
    <v-row align="center" justify="center">
      <v-col cols="12">
        <h1>로그인</h1>
      </v-col>
      <v-col cols="12">
          <a href="http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:3000/oauth/redirect">
            <img width="250" height="40" :src="require('../assets/google_login.png')"/>
          </a>
      </v-col>
      <v-col cols="12">
          <a href="http://localhost:8080/oauth2/authorization/naver?redirect_uri=http://localhost:3000/oauth/redirect">
            <img width="250" height="40" :src="require('../assets/naver_login.png')"/>
          </a>
      </v-col>
      <v-col cols="12">
          <a href="http://localhost:8080/oauth2/authorization/kakao?redirect_uri=http://localhost:3000/oauth/redirect">
            <img width="250" height="40" :src="require('../assets/kakao_login.png')"/>
          </a>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  
}
</script>

<style>

</style>