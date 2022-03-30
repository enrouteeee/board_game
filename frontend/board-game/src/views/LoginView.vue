<template>
  <container class="mx-auto">
    <v-row>
      <v-form style="width: 400px; height: 500">
        <div>
          <v-icon color="black" size="30px">person</v-icon>
          <div>
            <v-text-field placeholder="email" v-model="email" required >
            </v-text-field>
          </div>
        </div>
        <div>
          <v-icon color="black" size="30px">lock</v-icon>
          <div>
            <v-text-field placeholder="password" type="password" v-model="password" required >
            </v-text-field>
          </div>
        </div>
        <div style="height: 15px;"></div>
        <v-btn color="#2c4f91" dark large block @click="loginSubmit" >Login</v-btn >
        <div style="height: 15px;"></div>
        <v-btn color="#2c4f91" dark large block @click.stop="dialog = true" >SingUp</v-btn >
          <v-dialog v-model="dialog" max-width="400">
            <v-card>
              <v-card-title class="text-h5">회원 정보 입력</v-card-title>
              <v-card-text>
                <v-container>
                  <v-col>
                    <v-col>
                      <v-text-field
                        label="email"
                        required
                        v-model="signupEmail"
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field
                        label="password"
                        type="password"
                        required
                        v-model="signupPassword"
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field
                        label="nickname"
                        required
                        v-model="signupNickname"
                      ></v-text-field>
                    </v-col>
                  </v-col>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click=signup>만들기</v-btn>
                <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
      </v-form>
    </v-row>
  </container>
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
              console.log('로그인 성공');
              this.$store.commit('setNickname', res.data.nickname);
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