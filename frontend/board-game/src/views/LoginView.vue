<template>
  <container class="mx-auto">
    <v-row>
      <v-form style="width: 400px; height: 500">
        <div>
          <v-icon color="black" size="35px">person</v-icon>
          <div>
            <v-text-field placeholder="email" v-model="email" required >
            </v-text-field>
          </div>
        </div>
        <div>
          <v-icon color="black" size="35px">lock</v-icon>
          <div>
            <v-text-field placeholder="password" type="password" v-model="password" required >
            </v-text-field>
          </div>
        </div>
        <v-btn color="#2c4f91" dark large block @click="loginSubmit" >Login</v-btn >
      </v-form>
    </v-row>
  </container>
</template>

<script>
export default {
  data() {
    return {
      email: null,
      password: null
    };
  },
  methods: {
    loginSubmit() {
      let saveData = {};
      saveData.email = this.email;
      saveData.password = this.password;

      try { 
        this.$axios
          .post("/api/login", saveData, { withCredentials: true })
          .then((res) => {
            if (res.status === 200) {
              console.log('로그인 성공');
              this.$router.push("/room-list");
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