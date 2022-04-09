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
  data() {
    return {
      email: null,
      password: null,
      dialog: false,
      signupEmail: null,
      signupPassword: null,
      signupNickname: null,
    };
  },
  methods: {
    loginSubmit() {
      let request = {};
      request.email = this.email;
      request.password = this.password;

      try { 
        this.$axios
          .post("/api/login", request, { withCredentials: true })
          .then((res) => {
            if (res.status === 200) {
              console.log('로그인 성공', res.data);
              this.$store.commit('setNickname', res.data.nickname);
              this.$store.commit('setUserId', res.data.id);
              this.$router.push("/room-list");
            }
          });
      } catch (error) {
        console.log(error);
      }
    },
    signup() {
      let request = {};
      request.email = this.signupEmail;
      request.password = this.signupPassword;
      request.nickname = this.signupNickname;

      this.signupEmail = null;
      this.signupPassword = null;
      this.signupNickname = null;

      try { 
        this.$axios
          .post("/api/signup", request, { withCredentials: true })
          .then((res) => {
            if (res.status === 200) {
              console.log('회원가입 성공');
              this.dialog = false;
            }
          });
      } catch (error) {
        console.log(error);
      }
    },
  }
}
</script>

<style>

</style>