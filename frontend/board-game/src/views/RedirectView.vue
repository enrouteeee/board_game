<template>
  <v-container>
    <v-row>
      <v-dialog v-model="setNicknameModal" max-width="400" persistent>
            <v-card>
              <v-card-title class="text-h5">닉네임 입력</v-card-title>
              <v-card-text>
                <v-container>
                  <v-col>
                    <v-col>
                      <v-text-field
                        label="닉네임"
                        required
                        v-model="newNickname"
                      ></v-text-field>
                    </v-col>
                  </v-col>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click=setNickname>등록하기</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      setNicknameModal : false,
      userId: "",
      newNickname: "",
    };
  },
  created() {
    const token = this.$route.query.token;
    console.log('token', token);

    if(token) {
      this.$store.commit('setToken', token);
    }

    try { 
      this.$axios
        .get("/api/user", {
          headers: {
            'Auth': this.$store.state.token
          }
        })
        .then((res) => {
          if (res.status === 200) {
            console.log('로그인 성공', res.data);

            if(res.data.nickname === null) {
              console.log("여기");
              this.userId = res.data.id;
              this.setNicknameModal = true;
            } else {
              console.log(res.data.id, res.data.nickname);
              this.setUserIdAndNickname(res.data.id, res.data.nickname);
            }
          }
      });
    } catch (error) {
      console.log(error);
    }
  },
  methods: {
    setNickname() {
      try { 
        this.$axios
          .post("/api/user", this.newNickname, {
            withCredentials: true,
            headers: {
              'Auth': this.$store.state.token,
              'Content-Type': 'application/json;charset=UTF-8'
            }
          })
          .then((res) => {
            if (res.status === 200) {
              this.setNicknameModal = false;
              console.log(this.userId, this.newNickname);
              this.setUserIdAndNickname(this.userId, this.newNickname);
            }
          });
      } catch (error) {
        console.log(error);
      }
    },
    setUserIdAndNickname(userId, nickname) {
      this.$store.commit('setNickname', nickname);
      this.$store.commit('setUserId', userId);
      this.$router.push("/room-list");
    }
  }
}
</script>

<style>

</style>